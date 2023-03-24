package com.megazone.bi.service.impl;

import com.megazone.bi.common.BiPatch;
import com.megazone.bi.mapper.BiFunnelMapper;
import com.megazone.bi.service.BiFunnelService;
import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.feign.crm.service.CrmService;
import com.megazone.core.utils.BiTimeUtil;
import com.megazone.core.utils.UserCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BiFunnelServiceImpl implements BiFunnelService {

	@Autowired
	private BiFunnelMapper biFunnelMapper;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CrmService crmService;

	@Override
	public List<JSONObject> sellFunnel(BiParams biParams) {
		Integer menuId = 103;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		List<JSONObject> list = new ArrayList<>();
		List<Long> userIds = record.getUserIds();
		if (userIds.size() == 0) {
			return list;
		}
		Map<String, Object> map = record.toMap();
		map.put("typeId", biParams.getTypeId());
		list = biFunnelMapper.sellFunnel(map);
		return list;
	}

	@Override
	public List<JSONObject> addBusinessAnalyze(BiParams biParams) {
		Integer menuId = 103;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		Integer cycleNum = record.getCycleNum();
		List<Long> userIds = record.getUserIds();
		Integer beginTime = record.getBeginTime();
		List<JSONObject> list = new ArrayList<>();
		if (userIds.size() == 0) {
			return list;
		}
		List<Integer> timeList = new ArrayList<>();
		for (int i = 1; i <= cycleNum; i++) {
			timeList.add(beginTime);
			beginTime = BiTimeUtil.estimateTime(beginTime);
		}
		Map<String, Object> map = record.toMap();
		map.put("timeList", timeList);
		List<JSONObject> jsonList = biFunnelMapper.addBusinessAnalyze(map);
		BiPatch.supplementJsonList(jsonList, "type", timeList, "businessMoney", "businessNum");
		jsonList.sort(Comparator.comparing(jsonObject -> jsonObject.getString("type")));
		return jsonList;
	}

	@Override
	public BasePage<JSONObject> sellFunnelList(BiParams biParams) {
		Integer menuId = 103;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		BasePage<JSONObject> parse = new BasePage<>(biParams.getPage(), biParams.getLimit());
		BasePage<JSONObject> page = biFunnelMapper.sellFunnelList(parse, record.getUserIds(), record.getSqlDateFormat(), biParams.getType());
		page.getList().forEach(object -> {
			if (object.getLong("createUserId") != null) {
				object.put("createUserName", UserCacheUtil.getUserName(object.getLong("createUserId")));
			}
			if (object.getLong("ownerUserId") != null) {
				object.put("ownerUserName", UserCacheUtil.getUserName(object.getLong("ownerUserId")));
			}
			if (object.getInteger("customerId") != null) {
				List<SimpleCrmEntity> crmEntities = crmService.queryCustomerInfo(Collections.singleton(object.getInteger("customerId"))).getData();
				object.put("customerName", crmEntities.size() > 0 ? crmEntities.get(0).getName() : "");
			}
		});
		return page;
	}

	@Override
	public List<JSONObject> win(BiParams biParams) {
		Integer menuId = 103;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		Integer cycleNum = record.getCycleNum();
		List<Long> userIds = record.getUserIds();
		Integer beginTime = record.getBeginTime();
		List<JSONObject> list = new ArrayList<>();
		if (userIds.size() == 0) {
			return list;
		}
		List<Integer> timeList = new ArrayList<>();
		for (int i = 1; i <= cycleNum; i++) {
			timeList.add(beginTime);
			beginTime = BiTimeUtil.estimateTime(beginTime);
		}
		Map<String, Object> map = record.toMap();
		map.put("timeList", timeList);
		List<JSONObject> jsonList = biFunnelMapper.win(map);
		BiPatch.supplementJsonList(jsonList, "type", timeList, "businessNum", "businessEnd", "proportion");
		jsonList.sort(Comparator.comparing(jsonObject -> jsonObject.getString("type")));
		return jsonList;
	}
}
