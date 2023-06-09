package com.megazone.bi.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.megazone.bi.entity.VO.BiPageVO;
import com.megazone.bi.entity.VO.BiParamVO;
import com.megazone.bi.mapper.BiWorkMapper;
import com.megazone.bi.service.BiWorkService;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.feign.examine.entity.ExamineInfoVo;
import com.megazone.core.feign.examine.service.ExamineService;
import com.megazone.core.feign.oa.OaService;
import com.megazone.core.feign.oa.entity.ExamineVO;
import com.megazone.core.utils.BiTimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BiWorkServiceImpl implements BiWorkService {

	@Autowired
	private AdminService adminService;

	@Autowired
	private BiWorkMapper biWorkMapper;

	@Autowired
	private ExamineService examineService;
	@Autowired
	private OaService oaService;

	/**
	 * Query log statistics
	 *
	 * @param biParams params
	 */
	@Override
	public List<JSONObject> logStatistics(BiParams biParams) {
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeTypeOa(biParams);
		List<JSONObject> records = new ArrayList<>();
		for (Long userId : record.getUserIds()) {
			Map<String, Object> map = record.toMap();
			map.put("userId", userId);
			List<JSONObject> recordList = biWorkMapper.logStatistics(map);
			if (recordList.size() > 0) {
				JSONObject userRecord = new JSONObject(new HashMap<>(recordList.get(0).getInnerMapObject())).fluentRemove("sum").fluentRemove("sendUserIds").fluentRemove("readUserIds");
				int commentCount = 0, unCommentCount = 0, count = recordList.size();
				for (JSONObject task : recordList) {
					if (task.getInteger("sum") > 0) {
						commentCount++;
					} else {
						unCommentCount++;
					}
				}
				userRecord.fluentPut("commentCount", commentCount).fluentPut("unCommentCount", unCommentCount).fluentPut("count", count);
				records.add(userRecord);
			}
		}
		return records;
	}

	/**
	 * Query approval statistics
	 *
	 * @param biParams params
	 */
	@Override
	public JSONObject examineStatistics(BiParams biParams) {
		JSONObject object = new JSONObject();
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeTypeOa(biParams);
// List<JSONObject> categoryList = biWorkMapper.queryExamineCategory();
		List<ExamineInfoVo> categoryList = examineService.queryNormalExamine(0).getData();
		List<Long> users = record.getUserIds();
		if (users.size() == 0 || CollUtil.isEmpty(categoryList)) {
			object.put("userList", users);
			object.put("categoryList", new ArrayList<>());
		} else {
			List<JSONObject> categories = new ArrayList<>();
			categoryList.forEach(examineInfo -> {
				JSONObject category = new JSONObject();
				category.put("title", examineInfo.getExamineName());
				category.put("categoryId", examineInfo.getExamineId());
				category.put("type", examineInfo.getOaType());
				categories.add(category);
			});
			object.put("categoryList", categories);
			Map<String, Object> map = record.toMap();
			map.put("categorys", categoryList);
			List<JSONObject> userList = biWorkMapper.examineStatistics(map);
			userList = userList.stream().map(jsonObject -> {
				JSONObject o = new JSONObject();
				jsonObject.getInnerMapObject().forEach((k, v) -> {
					if (k.startsWith("count")) {
						o.put(k.replace("count", "count_"), v);
					} else {
						o.put(k, v);
					}
				});
				return o;
			}).collect(Collectors.toList());
			object.put("userList", userList);
		}
		return object;
	}

	@Override
	public BiPageVO<JSONObject> examineInfo(BiParamVO biParamVO) {
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeTypeOa(biParamVO);
		String sqlDateFormat = record.getSqlDateFormat();
		BasePage<JSONObject> page = new BasePage<>(biParamVO.getPage(), biParamVO.getLimit());
		BiPageVO<JSONObject> recordPageVO = new BiPageVO<>();
		BasePage<ExamineVO> recordPage = biWorkMapper.myInitiate(page, sqlDateFormat, biParamVO.getUserId(), biParamVO.getCategoryId(), record.getBeginTime(), record.getFinalTime());
		if (recordPage == null) {
			return recordPageVO;
		}
		Result<List<ExamineVO>> transfer = oaService.transfer(recordPage.getList());
		recordPage.setList(transfer.getData());
		JSONObject jsonObject = biWorkMapper.queryExamineCount(sqlDateFormat, biParamVO.getUserId(), biParamVO.getCategoryId(), record.getBeginTime(), record.getFinalTime());
		BeanUtils.copyProperties(recordPage, recordPageVO);
		recordPageVO.setDuration(jsonObject.get("duration"));
		recordPageVO.setMoney(jsonObject.get("money"));
		return recordPageVO;
	}
}
