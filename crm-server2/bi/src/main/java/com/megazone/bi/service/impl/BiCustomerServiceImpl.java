package com.megazone.bi.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.bi.common.BiPatch;
import com.megazone.bi.mapper.BiCustomerMapper;
import com.megazone.bi.service.BiCustomerService;
import com.megazone.bi.service.BiEsStatisticsService;
import com.megazone.core.common.Const;
import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.feign.crm.service.CrmService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.utils.BiTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class BiCustomerServiceImpl implements BiCustomerService {

	@Autowired
	private BiCustomerMapper biCustomerMapper;

	@Autowired
	private CrmService crmService;

	@Autowired
	private BiEsStatisticsService biEsStatisticsService;

	@Override
	public List<JSONObject> totalCustomerStats(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity timeEntity = BiTimeUtil.analyzeType(biParams);
		Integer cycleNum = timeEntity.getCycleNum();
		Integer beginTime = timeEntity.getBeginTime();
		List<Integer> timeList = new ArrayList<>();
		for (int i = 1; i <= cycleNum; i++) {
			timeList.add(beginTime);
			beginTime = BiTimeUtil.estimateTime(beginTime);
		}
		List<JSONObject> customerNumObjectList = biEsStatisticsService.getStatisticsCustomerInfo(timeEntity, false);
		if (customerNumObjectList.size() == 0) {
			for (Integer type : timeList) {
				customerNumObjectList.add(new JSONObject().fluentPut("type", type).fluentPut("customerNum", 0));
			}
		}
		List<JSONObject> dealNumList = biEsStatisticsService.getStatisticsCustomerInfo(timeEntity, true);
		List<JSONObject> jsonObjectList = biEsStatisticsService.mergeJsonObjectList(customerNumObjectList, dealNumList);
		BiPatch.supplementJsonList(jsonObjectList, "type", timeList, "customerNum", "dealCustomerNum");
		jsonObjectList.sort(Comparator.comparing(jsonObject -> jsonObject.getString("type")));
		return jsonObjectList;
	}


	private void handleTableName(List<String> tableNameList) {
		String firstYear = biCustomerMapper.queryFirstCustomerCreteTime();
		Integer minYear;
		if (StrUtil.isNotEmpty(firstYear)) {
			minYear = Integer.valueOf(firstYear);
		} else {
			minYear = DateUtil.thisYear();
		}
		Integer thisYear = DateUtil.thisYear();
		List<String> tableNames = BiPatch.getYearsOrTableNameList(minYear, thisYear, true);
		tableNameList.removeIf(t -> !tableNames.contains(t));
	}

	@Override
	public JSONObject totalCustomerTable(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity timeEntity = BiTimeUtil.analyzeType(biParams);
		List<Long> userIds = timeEntity.getUserIds();
		if (userIds.size() == 0) {
			return new JSONObject().fluentPut("list", new ArrayList<>()).fluentPut("total", new JSONObject());
		}
		List<JSONObject> recordList = biCustomerMapper.totalCustomerTable(timeEntity);
		JSONObject total = new JSONObject();
		total.put("customerNum", new BigDecimal("0"));
		total.put("dealCustomerNum", new BigDecimal("0"));
		total.put("contractMoney", new BigDecimal("0"));
		total.put("receivablesMoney", new BigDecimal("0"));
		recordList.forEach(r -> {
			total.put("realname", "total");
			total.put("customerNum", total.getBigDecimal("customerNum").add(r.getBigDecimal("customerNum")));
			total.put("dealCustomerNum", total.getBigDecimal("dealCustomerNum").add(r.getBigDecimal("dealCustomerNum")));
			total.put("contractMoney", total.getBigDecimal("contractMoney").add(r.getBigDecimal("contractMoney")));
			total.put("receivablesMoney", total.getBigDecimal("receivablesMoney").add(r.getBigDecimal("receivablesMoney")));
			r.put("dealCustomerRate", r.getInteger("customerNum") != 0 ? r.getInteger("dealCustomerNum") * 100 / r.getInteger("customerNum") : 0);
		});
		return new JSONObject().fluentPut("list", recordList).fluentPut("total", total);
	}

	@Override
	public List<JSONObject> customerRecordStats(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		Integer cycleNum = record.getCycleNum();
		Integer beginTime = record.getBeginTime();
		List<Integer> timeList = new ArrayList<>();
		for (int i = 1; i <= cycleNum; i++) {
			timeList.add(beginTime);
			beginTime = BiTimeUtil.estimateTime(beginTime);
		}
		Map<String, Object> map = record.toMap();
		map.put("timeList", timeList);
		return biCustomerMapper.customerRecordStats(map);
	}

	@Override
	public JSONObject customerRecordInfo(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		List<Long> userIds = record.getUserIds();
		if (userIds.size() == 0) {
			return new JSONObject().fluentPut("list", new ArrayList<>()).fluentPut("total", new JSONObject());
		}
		List<JSONObject> recordList = biCustomerMapper.customerRecordInfo(record);
		JSONObject total = new JSONObject().fluentPut("realname", "total").fluentPut("customerCount", 0);
		for (JSONObject object : recordList) {
			total.put("customerCount", total.getInteger("customerCount") + object.getInteger("customerCount"));
		}
		return new JSONObject().fluentPut("list", recordList).fluentPut("total", total);
	}

	@Override
	public List<JSONObject> customerRecodCategoryStats(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		return biCustomerMapper.customerRecordCategoryStats(record);
	}

	@Override
	public List<JSONObject> customerConversionStats(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		Integer cycleNum = record.getCycleNum();
		Integer beginTime = record.getBeginTime();
		List<Integer> timeList = new ArrayList<>();
		for (int i = 1; i <= cycleNum; i++) {
			timeList.add(beginTime);
			beginTime = BiTimeUtil.estimateTime(beginTime);
		}
		Map<String, Object> map = record.toMap();
		map.put("timeList", timeList);
		JSONObject result = biCustomerMapper.customerConversionStats(map);
		if (result == null) {
			Map<String, Object> objectMap = new HashMap<>(8);
			timeList.forEach(time -> objectMap.put(String.valueOf(time), 0.0));
			result = new JSONObject(objectMap);
		}
		List<JSONObject> recordList = new ArrayList<>();
		Map<String, Object> columns = result.getInnerMapObject();
		for (String type : columns.keySet()) {
			recordList.add(new JSONObject().fluentPut("type", type).fluentPut("pro", columns.get(type) == null ? 0 : columns.get(type)));
		}
		recordList.sort(Comparator.comparing(record1 -> record1.getInteger("type")));
		return recordList;
	}

	@Override
	public BasePage<JSONObject> customerConversionInfo(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		String sqlDateFormat = record.getSqlDateFormat();
		BasePage<JSONObject> page = new BasePage<>(biParams.getPage(), biParams.getLimit());
		return biCustomerMapper.customerConversionInfo(page, sqlDateFormat, record.getUserIds(), biParams.getType());
	}

	@Override
	public List<JSONObject> poolStats(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		Integer cycleNum = record.getCycleNum();
		Integer beginTime = record.getBeginTime();
		List<Integer> timeList = new ArrayList<>();
		for (int i = 1; i <= cycleNum; i++) {
			timeList.add(beginTime);
			beginTime = BiTimeUtil.estimateTime(beginTime);
		}
		Map<String, Object> map = record.toMap();
		map.put("timeList", timeList);
		return biCustomerMapper.poolStats(map);
	}

	@Override
	public JSONObject poolTable(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		List<JSONObject> recordList = biCustomerMapper.poolTable(record);
		JSONObject total = new JSONObject().fluentPut("realname", "total").fluentPut("deptName", "").fluentPut("putInNum", 0).fluentPut("receiveNum", 0);
		recordList.forEach(r -> {
			total.put("putInNum", total.getInteger("putInNum") + r.getInteger("putInNum"));
			total.put("receiveNum", total.getInteger("receiveNum") + r.getInteger("receiveNum"));
		});
		return new JSONObject().fluentPut("list", recordList).fluentPut("total", total);
	}

	@Override
	public List<JSONObject> employeeCycle(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		Integer cycleNum = record.getCycleNum();
		Integer beginTime = record.getBeginTime();
		List<Integer> timeList = new ArrayList<>();
		for (int i = 1; i <= cycleNum; i++) {
			timeList.add(beginTime);
			beginTime = BiTimeUtil.estimateTime(beginTime);
		}
		Map<String, Object> map = record.toMap();
		List<JSONObject> jsonObjectList = biCustomerMapper.employeeCycle(map);
		if (jsonObjectList != null) {
			jsonObjectList.forEach(jsonObject -> {
				BigDecimal cycle = jsonObject.getBigDecimal("cycle");
				if (cycle != null && cycle.doubleValue() >= 0) {
					jsonObject.put("cycle", cycle.setScale(1, BigDecimal.ROUND_HALF_UP));
				} else {
					jsonObject.put("cycle", 0);
				}
			});
		}
		BiPatch.supplementJsonList(jsonObjectList, "type", timeList, "cycle", "customerNum");
		jsonObjectList.sort(Comparator.comparing(jsonObject -> jsonObject.getString("type")));
		return jsonObjectList;
	}

	@Override
	public JSONObject employeeCycleInfo(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		List<Long> userIds = record.getUserIds();
		if (userIds.size() == 0) {
			return new JSONObject().fluentPut("list", new ArrayList<>()).fluentPut("total", new JSONObject());
		}
		List<JSONObject> recordList = biCustomerMapper.employeeCycleInfo(record);
		JSONObject total = new JSONObject().fluentPut("realname", "total").fluentPut("customerNum", 0).fluentPut("cycle", 0);
		recordList.forEach(r -> {
			total.put("customerNum", total.getDouble("customerNum") + r.getDouble("customerNum"));
			total.put("cycle", total.getDouble("cycle") + r.getDouble("cycle"));
		});
		return new JSONObject().fluentPut("list", recordList).fluentPut("total", total);
	}

	@Override
	public JSONObject districtCycle(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		List<Long> userIds = record.getUserIds();
		if (userIds.size() == 0) {
			return new JSONObject().fluentPut("list", new ArrayList<>()).fluentPut("total", new JSONObject());
		}
		String[] districtArr = new String[]{"강원도", "경기도", "경상남도", "경상북도", "광주광역시",
				"대구광역시", "대전광역시", "부산광역시", "서울특별시", "세종특별자치시", "울산광역시", "인천광역시",
				"전라남도", "전라북도", "제주특별자치도", "충청남도", "충청북도"};
		List<JSONObject> recordList = biCustomerMapper.districtCycle(record, Arrays.asList(districtArr));
		JSONObject total = new JSONObject().fluentPut("type", "total").fluentPut("customerNum", 0).fluentPut("cycle", 0);
		recordList.forEach(r -> {
			total.put("customerNum", total.getInteger("customerNum") + r.getInteger("customerNum"));
			total.put("cycle", total.getInteger("cycle") + r.getInteger("cycle"));
		});
		return new JSONObject().fluentPut("list", recordList).fluentPut("total", total);
	}

	@Override
	public JSONObject productCycle(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		List<Long> userIds = record.getUserIds();
		List<SimpleCrmEntity> productList = ApplicationContextHolder.getBean(CrmService.class).queryProductInfo().getData();
		if (userIds.size() == 0 || productList.size() == 0) {
			return new JSONObject().fluentPut("list", new ArrayList<>()).fluentPut("total", new JSONObject());
		}
		List<JSONObject> recordList = biCustomerMapper.productCycle(record, productList);
		JSONObject total = new JSONObject().fluentPut("productName", "total").fluentPut("customerNum", 0).fluentPut("cycle", 0);
		recordList.forEach(r -> {
			total.put("customerNum", total.getInteger("customerNum") + r.getInteger("customerNum"));
			total.put("cycle", total.getInteger("cycle") + r.getInteger("cycle"));
		});
		return new JSONObject().fluentPut("list", recordList).fluentPut("total", total);
	}

	@Override
	public List<JSONObject> customerSatisfactionTable(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		List<Long> userIds = record.getUserIds();
		if (userIds.size() == 0) {
			return new ArrayList<>();
		}

		JSONObject object = biCustomerMapper.querySatisfactionOptionList();
		if (object == null) {
			return new ArrayList<>();
		}
		Map<String, Object> map = record.toMap();
		object.put("options", StrUtil.splitTrim(object.getString("options"), Const.SEPARATOR));
		map.putAll(object.getInnerMapObject());
		return biCustomerMapper.customerSatisfactionTable(map);
	}

	@Override
	public List<JSONObject> productSatisfactionTable(BiParams biParams) {
		Integer menuId = 104;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		List<SimpleCrmEntity> productList = crmService.queryProductInfo().getData();
		if (CollectionUtil.isEmpty(productList)) {
			return new ArrayList<>();
		}
		JSONObject object = biCustomerMapper.querySatisfactionOptionList();
		if (object == null) {
			return new ArrayList<>();
		}
		String options = object.getString("options");
		if (options.contains(",")) {
			object.put("options", Arrays.asList(options.split(",")));
		} else {
			object.put("options", ListUtil.toList(options));
		}
		Map<String, Object> map = record.toMap();
		map.putAll(object.getInnerMapObject());
		map.put("products", productList);
		return biCustomerMapper.productSatisfactionTable(map);
	}
}
