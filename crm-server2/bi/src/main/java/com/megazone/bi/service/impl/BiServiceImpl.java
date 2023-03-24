package com.megazone.bi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.megazone.bi.common.MonthEnum;
import com.megazone.bi.entity.PO.CrmAchievement;
import com.megazone.bi.entity.VO.ProductStatisticsVO;
import com.megazone.bi.mapper.BiMapper;
import com.megazone.bi.service.BiService;
import com.megazone.bi.service.ICrmAchievementService;
import com.megazone.core.common.JSONArray;
import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.feign.crm.service.CrmService;
import com.megazone.core.utils.BiTimeUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BiServiceImpl implements BiService {

	@Autowired
	private BiMapper biMapper;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CrmService crmService;

	@Autowired
	private ICrmAchievementService crmAchievementService;

	/**
	 * Product sales statistics
	 * startTime start time endTime end time userId user ID deptId department ID
	 */
	@Override
	public BasePage<ProductStatisticsVO> queryProductSell(BiParams biParams) {
		Integer menuId = 118;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity timeEntity = BiTimeUtil.analyzeType(biParams);
		Integer page = biParams.getPage() > 0 ? (biParams.getPage() - 1) * biParams.getLimit() : 0;
		timeEntity.setPage(page).setLimit(biParams.getLimit());
		JSONObject object = biMapper.queryProductSellCount(timeEntity);
		Integer count = object.getInteger("count");
		if (count == 0) {
			BasePage<ProductStatisticsVO> basePage = new BasePage<>();
			basePage.setExtraData(object);
			return basePage;
		}
		List<ProductStatisticsVO> productSell = biMapper.queryProductSell(timeEntity);
		BasePage<ProductStatisticsVO> basePage = new BasePage<>(biParams.getPage(), biParams.getLimit(), count);
		basePage.setList(productSell);
		basePage.setExtraData(object);
		return basePage;
	}

	@Override
	public List<Map<String, Object>> productSellExport(BiParams biParams) {
		Integer menuId = 118;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity timeEntity = BiTimeUtil.analyzeType(biParams);
		timeEntity.setPage(0).setLimit(10000);
		List<ProductStatisticsVO> productSell = biMapper.queryProductSell(timeEntity);
		return productSell.stream().map(BeanUtil::beanToMap).collect(Collectors.toList());
	}

	@Override
	public List<JSONObject> taskCompleteStatistics(String year, Integer status, Integer deptId, Long userId, Integer isUser) {
		Integer menuId = 102;
		List<JSONObject> resultList = new ArrayList<>();
		JSONObject kv = new JSONObject().fluentPut("map", MonthEnum.values()).fluentPut("year", year);
		if (isUser == 1) {
			if (userId != null) {
				//Filter userId according to data permissions
				userId = BiTimeUtil.dataFilter(menuId, userId);
				kv.fluentPut("userId", userId).fluentPut("status", status).fluentPut("type", 3).fluentPut("objId", userId);
				List<JSONObject> recordList = biMapper.taskCompleteStatistics(kv);
				if (recordList.size() == 0) {
					return new ArrayList<>();
				}
				resultList.add(new JSONObject().fluentPut("name", UserCacheUtil.getUserName(userId)).fluentPut("list", recordList));
			} else {
				List<CrmAchievement> achievementList = crmAchievementService.lambdaQuery().eq(CrmAchievement::getType, 3).eq(CrmAchievement::getStatus, status).eq(CrmAchievement::getYear, year).list();
				//Filter userId according to data permissions
				List<Long> userIdList = achievementList.stream().map(record -> record.getObjId().longValue()).collect(Collectors.toList());
				BiTimeUtil.dataFilter(menuId, userIdList);
				achievementList.removeIf(user -> !userIdList.contains(user.getObjId().longValue()));
				for (CrmAchievement record : achievementList) {
					Long user = record.getObjId().longValue();
					String name = UserCacheUtil.getUserName(user);
					kv.fluentPut("userId", user).fluentPut("status", status).fluentPut("type", 3).fluentPut("objId", user);
					List<JSONObject> recordList = biMapper.taskCompleteStatistics(kv);
					resultList.add(new JSONObject().fluentPut("name", name).fluentPut("list", recordList));
				}
			}
		} else {
			List<Integer> deptIdList;
			if (deptId != null) {
				deptIdList = adminService.queryChildDeptId(deptId).getData();
				deptIdList.add(deptId);
			} else {
				List<CrmAchievement> achievementList = crmAchievementService.lambdaQuery().eq(CrmAchievement::getType, 2).eq(CrmAchievement::getStatus, status).eq(CrmAchievement::getYear, year).list();
				deptIdList = achievementList.stream().map(CrmAchievement::getObjId).collect(Collectors.toList());
			}
			//Filter deptId according to data permissions
			if (!UserUtil.isAdmin()) {
				List<Integer> authDeptIdList = new ArrayList<>();
				Integer dataType = adminService.queryDataType(UserUtil.getUserId(), menuId).getData();
				if (dataType == null || dataType == 1 || dataType == 2) {
					deptIdList.clear();
				} else if (dataType != 5) {
					if (dataType == 3) {
						authDeptIdList.add(UserUtil.getUser().getDeptId());
					} else if (dataType == 4) {
						authDeptIdList = adminService.queryChildDeptId(UserUtil.getUser().getDeptId()).getData();
						authDeptIdList.add(UserUtil.getUser().getDeptId());
					}
					deptIdList.retainAll(authDeptIdList);
				}
			}
			for (Integer dept : deptIdList) {
				String name = adminService.queryDeptName(dept).getData();
				kv.fluentPut("deptId", dept).fluentPut("status", status).fluentPut("type", 2).fluentPut("objId", dept);
				List<JSONObject> recordList = biMapper.taskCompleteStatistics(kv);
				resultList.add(new JSONObject().fluentPut("name", name).fluentPut("list", recordList));
			}
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> taskCompleteStatisticsExport(String year, Integer status, Integer deptId, Long userId, Integer isUser) {
		List<JSONObject> recordList = taskCompleteStatistics(year, status, deptId, userId, isUser);
		List<Map<String, Object>> list = new ArrayList<>();
		recordList.forEach(record -> {
			String name = record.getString("name");
			JSONArray taskList = record.getJSONArray("list");
			BigDecimal quarterMoney = new BigDecimal(0.00);
			BigDecimal quarterAchievement = new BigDecimal(0.00);
			BigDecimal totalMoney = new BigDecimal(0.00);
			BigDecimal totalAchievement = new BigDecimal(0.00);
			for (int i = 0; i < taskList.size(); i++) {
				JSONObject task = taskList.getJSONObject(i);
				quarterMoney = quarterMoney.add(task.getBigDecimal("money"));
				totalMoney = totalMoney.add(task.getBigDecimal("money"));
				quarterAchievement = quarterAchievement.add(task.getBigDecimal("achievement"));
				totalAchievement = totalAchievement.add(task.getBigDecimal("achievement"));
				task.fluentPut("name", name);
				list.add(task.getInnerMapObject());
				if (i == 2) {
					BigDecimal rate = quarterMoney.divide(quarterAchievement, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					JSONObject r = new JSONObject().fluentPut("name", "").fluentPut("month", "Q1").fluentPut("money", quarterMoney).fluentPut("achievement", quarterAchievement).fluentPut("rate", rate);
					list.add(r.getInnerMapObject());
					quarterMoney = new BigDecimal(0.00);
					quarterAchievement = new BigDecimal(0.00);
				} else if (i == 5) {
					BigDecimal rate = quarterMoney.divide(quarterAchievement, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					JSONObject r = new JSONObject().fluentPut("name", "").fluentPut("month", "Q2").fluentPut("money", quarterMoney).fluentPut("achievement", quarterAchievement).fluentPut("rate", rate);
					list.add(r.getInnerMapObject());
					quarterMoney = new BigDecimal(0.00);
					quarterAchievement = new BigDecimal(0.00);
				} else if (i == 8) {
					BigDecimal rate = quarterMoney.divide(quarterAchievement, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					JSONObject r = new JSONObject().fluentPut("name", "").fluentPut("month", "Q3").fluentPut("money", quarterMoney).fluentPut("achievement", quarterAchievement).fluentPut("rate", rate);
					list.add(r.getInnerMapObject());
					quarterMoney = new BigDecimal(0.00);
					quarterAchievement = new BigDecimal(0.00);
				} else if (i == 11) {
					BigDecimal rate = quarterMoney.divide(quarterAchievement, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					JSONObject r = new JSONObject().fluentPut("name", "").fluentPut("month", "Q4").fluentPut("money", quarterMoney).fluentPut("achievement", quarterAchievement).fluentPut("rate", rate);
					list.add(r.getInnerMapObject());
					quarterMoney = new BigDecimal(0.00);
					quarterAchievement = new BigDecimal(0.00);
					BigDecimal totalRate = totalMoney.divide(totalAchievement, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					JSONObject total = new JSONObject().fluentPut("name", "").fluentPut("month", "full year").fluentPut("money", totalMoney).fluentPut("achievement", totalAchievement).fluentPut("rate", totalRate);
					list.add(total.getInnerMapObject());
				}
			}
		});
		return list;
	}
}
