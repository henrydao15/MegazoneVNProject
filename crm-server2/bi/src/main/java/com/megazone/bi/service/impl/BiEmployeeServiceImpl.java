package com.megazone.bi.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.megazone.bi.common.BiPatch;
import com.megazone.bi.mapper.BiEmployeeMapper;
import com.megazone.bi.service.BiEmployeeService;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.utils.BiTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BiEmployeeServiceImpl implements BiEmployeeService {

	@Autowired
	private BiEmployeeMapper biEmployeeMapper;

	@Override
	public List<JSONObject> contractNumStats(BiParams biParams) {
		Integer menuId = 106;
		biParams.setMenuId(menuId);
		String type = biParams.getType();
		Integer year = biParams.getYear();
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		int cycleNum = 12;
		Integer beginTime = year * 100 + 1;
		List<Integer> timeList = new ArrayList<>();
		for (int i = 1; i <= cycleNum; i++) {
			timeList.add(beginTime);
			beginTime = BiTimeUtil.estimateTime(beginTime);
		}
		List<JSONObject> recordList;
		Map<String, Object> map = record.toMap();
		map.put("timeList", timeList);
		if ("contractNum".equals(type)) {
			recordList = biEmployeeMapper.contractNum(map);
		} else if ("contractMoney".equals(type)) {
			recordList = biEmployeeMapper.contractMoney(map);
		} else if ("receivables".equals(type)) {
			recordList = biEmployeeMapper.receivables(map);
		} else {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		recordList.forEach(r -> {
			BigDecimal thisMonth = r.getBigDecimal("thisMonth");
			BigDecimal lastMonth = r.getBigDecimal("lastMonth");
			BigDecimal lastYear = r.getBigDecimal("lastYear");
			r.put("lastMonthGrowth", thisMonth.compareTo(new BigDecimal(0)) != 0 && lastMonth.compareTo(new BigDecimal(0)) != 0 ? (thisMonth.subtract(lastMonth)).multiply(new BigDecimal(100)).divide(lastMonth, 2, BigDecimal.ROUND_HALF_UP) : 0);
			r.put("lastYearGrowth", thisMonth.compareTo(new BigDecimal(0)) != 0 && lastYear.compareTo(new BigDecimal(0)) != 0 ? (thisMonth.subtract(lastYear)).multiply(new BigDecimal(100)).divide(lastYear, 2, BigDecimal.ROUND_HALF_UP) : 0);
			r.remove("lastMonth");
			r.remove("lastYear");
		});
		return recordList;
	}

	@Override
	public JSONObject totalContract(BiParams biParams) {
		Integer menuId = 106;
		biParams.setMenuId(menuId);
		biParams.setType("year");
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		Integer beginTime = record.getBeginTime();
		Integer cycleNum = record.getCycleNum();
		JSONObject total = biEmployeeMapper.totalContract(record);
		List<Integer> timeList = new ArrayList<>();
		for (int i = 1; i <= cycleNum; i++) {
			timeList.add(beginTime);
			beginTime = BiTimeUtil.estimateTime(beginTime);
		}
		Map<String, Object> map = record.toMap();
		map.put("timeList", timeList);
		List<JSONObject> recordList = biEmployeeMapper.totalContractTable(map);
		if (recordList.size() == 0) {

		}
		BiPatch.supplementJsonList(recordList, "type", timeList, "contractNum", "contractMoney", "receivablesMoney");
		recordList.sort(Comparator.comparing(jsonObject -> jsonObject.getString("type")));
		return total.fluentPut("list", recordList);
	}

	@Override
	public JSONObject invoiceStats(BiParams biParams) {
		Integer menuId = 106;
		biParams.setMenuId(menuId);
		Integer year = biParams.getYear();
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		DateTime dateTime = DateUtil.beginOfYear(DateUtil.parse(year.toString(), "yyyy"));
		JSONObject total = biEmployeeMapper.totalInvoice(dateTime, DateUtil.endOfYear(dateTime), record.getUserIds());
		Map<String, Object> map = new HashMap<>();
		map.put("userIds", record.getUserIds());
		List<JSONObject> objectList = new ArrayList<>(12);
		DateTime offset = DateUtil.beginOfMonth(dateTime);
		for (int i = 1; i <= 12; i++) {
			map.put("startTime", DateUtil.beginOfMonth(offset));
			map.put("endTime", DateUtil.endOfMonth(offset));
			JSONObject object = biEmployeeMapper.invoiceStatsTable(map);
			object.put("type", offset.toString("yyyy-MM"));
			objectList.add(object);
			offset = offset.offset(DateField.MONTH, 1);
		}
		return total.fluentPut("list", objectList);
	}
}
