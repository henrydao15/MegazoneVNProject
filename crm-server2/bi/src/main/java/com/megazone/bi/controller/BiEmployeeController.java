package com.megazone.bi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.bi.service.BiEmployeeService;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.utils.ExcelParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/biEmployee")
@Api(tags = "Employee Performance Analysis Module")
@Slf4j
public class BiEmployeeController {

	@Autowired
	private BiEmployeeService biEmployeeService;


	@ApiOperation("Contract Quantity Analysis")
	@PostMapping("/contractNumStats")
	public Result<List<JSONObject>> contractNumStats(@RequestBody BiParams biParams) {
		biParams.setType("contractNum");
		List<JSONObject> objectList = biEmployeeService.contractNumStats(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Contract quantity analysis export")
	@PostMapping("/contractNumStatsExport")
	public void contractNumStatsExport(@RequestBody BiParams biParams) {
		biParams.setType("contractNum");
		List<JSONObject> recordList = biEmployeeService.contractNumStats(biParams);
		List<Map<String, Object>> list = new ArrayList<>();
		List<String> monthList = new ArrayList<>();
		Map<String, Object> thisMonth = new HashMap<>();
		thisMonth.put("type", "Number of contracts in the current month (units)");
		Map<String, Object> lastMonthGrowth = new HashMap<>();
		lastMonthGrowth.put("type", "MoM growth (%)");
		Map<String, Object> lastYearGrowth = new HashMap<>();
		lastYearGrowth.put("type", "Year-on-year growth (%)");
		recordList.forEach(record -> {
			monthList.add(record.getString("month"));
			thisMonth.put(record.getString("month"), record.getString("thisMonth"));
			lastMonthGrowth.put(record.getString("month"), record.getString("lastMonthGrowth"));
			lastYearGrowth.put(record.getString("month"), record.getString("lastYearGrowth"));
		});
		list.add(thisMonth);
		list.add(lastMonthGrowth);
		list.add(lastYearGrowth);

		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("type", "date"));
		monthList.forEach(month -> dataList.add(ExcelParseUtil.toEntity(month, month)));
		ExcelParseUtil.exportExcel(recordList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Contract Quantity Analysis";
			}
		}, dataList);
	}

	@ApiOperation("Contract Amount Analysis")
	@PostMapping("/contractMoneyStats")
	public Result<List<JSONObject>> contractMoneyStats(@RequestBody BiParams biParams) {
		biParams.setType("contractMoney");
		List<JSONObject> objectList = biEmployeeService.contractNumStats(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Contract Amount Analysis Export")
	@PostMapping("/contractMoneyStatsExport")
	public void contractMoneyStatsExport(@RequestBody BiParams biParams) {
		biParams.setType("contractMoney");
		List<JSONObject> recordList = biEmployeeService.contractNumStats(biParams);
		List<Map<String, Object>> list = new ArrayList<>();
		List<String> monthList = new ArrayList<>();
		Map<String, Object> thisMonth = new HashMap<>();
		thisMonth.put("type", "Contract amount of the current month (yuan)");
		Map<String, Object> lastMonthGrowth = new HashMap<>();
		lastMonthGrowth.put("type", "MoM growth (%)");
		Map<String, Object> lastYearGrowth = new HashMap<>();
		lastYearGrowth.put("type", "Year-on-year growth (%)");
		recordList.forEach(record -> {
			monthList.add(record.getString("month"));
			thisMonth.put(record.getString("month"), record.getString("thisMonth"));
			lastMonthGrowth.put(record.getString("month"), record.getString("lastMonthGrowth"));
			lastYearGrowth.put(record.getString("month"), record.getString("lastYearGrowth"));
		});
		list.add(thisMonth);
		list.add(lastMonthGrowth);
		list.add(lastYearGrowth);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("type", "date"));
		monthList.forEach(month -> dataList.add(ExcelParseUtil.toEntity(month, month)));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Contract Amount Analysis";
			}
		}, dataList);
	}

	@ApiOperation("Analysis of payment amount")
	@PostMapping("/receivablesMoneyStats")
	public Result<List<JSONObject>> receivablesMoneyStats(@RequestBody BiParams biParams) {
		biParams.setType("receivables");
		List<JSONObject> objectList = biEmployeeService.contractNumStats(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Return amount analysis and export")
	@PostMapping("/receivablesMoneyStatsExport")
	public void receivablesMoneyStatsExport(@RequestBody BiParams biParams) {
		biParams.setType("receivables");
		List<JSONObject> recordList = biEmployeeService.contractNumStats(biParams);
		List<Map<String, Object>> list = new ArrayList<>();
		List<String> monthList = new ArrayList<>();
		Map<String, Object> thisMonth = new HashMap<>();
		thisMonth.put("type", "Amount to be collected in the current month (yuan)");
		Map<String, Object> lastMonthGrowth = new HashMap<>();
		lastMonthGrowth.put("type", "MoM growth (%)");
		Map<String, Object> lastYearGrowth = new HashMap<>();
		lastYearGrowth.put("type", "Year-on-year growth (%)");
		recordList.forEach(record -> {
			monthList.add(record.getString("month"));
			thisMonth.put(record.getString("month"), record.getString("thisMonth"));
			lastMonthGrowth.put(record.getString("month"), record.getString("lastMonthGrowth"));
			lastYearGrowth.put(record.getString("month"), record.getString("lastYearGrowth"));
		});
		list.add(thisMonth);
		list.add(lastMonthGrowth);
		list.add(lastYearGrowth);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("type", "date"));
		monthList.forEach(month -> dataList.add(ExcelParseUtil.toEntity(month, month)));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Analysis of payment amount";
			}
		}, dataList);
	}

	@ApiOperation("Contract Summary Table")
	@PostMapping("/totalContract")
	public Result<JsonNode> totalContract(@RequestBody BiParams biParams) {
		JSONObject jsonObject = biEmployeeService.totalContract(biParams);
		return Result.ok(jsonObject.node);
	}

	@ApiOperation("Contract summary table export")
	@PostMapping("/totalContractExport")
	public void totalContractExport(@RequestBody BiParams biParams) {
		JSONObject record = biEmployeeService.totalContract(biParams);
		List<JSONObject> recordList = record.getJSONArray("list").toJavaList(JSONObject.class);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("type", "date"));
		dataList.add(ExcelParseUtil.toEntity("contractNum", "Number of contracts signed (number)"));
		dataList.add(ExcelParseUtil.toEntity("contractMoney", "Contract contract amount (yuan)"));
		dataList.add(ExcelParseUtil.toEntity("receivablesMoney", "Receivable amount (yuan)"));
		ExcelParseUtil.exportExcel(recordList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Contract Summary";
			}
		}, dataList);
	}

	@ApiOperation("Invoice Statistics")
	@PostMapping("/invoiceStats")
	public Result<JsonNode> invoiceStats(@RequestBody BiParams biParams) {
		JSONObject jsonObject = biEmployeeService.invoiceStats(biParams);
		return Result.ok(jsonObject.node);
	}

	@ApiOperation("Invoice Statistics Export")
	@PostMapping("/invoiceStatsExport")
	public void invoiceStatsExport(@RequestBody BiParams biParams) {
		JSONObject jsonObject = biEmployeeService.invoiceStats(biParams);
		List<JSONObject> recordList = jsonObject.getJSONArray("list").toJavaList(JSONObject.class);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("type", "date"));
		dataList.add(ExcelParseUtil.toEntity("receivablesMoney", "Receivable amount (yuan)"));
		dataList.add(ExcelParseUtil.toEntity("invoiceMoney", "Invoice amount (yuan)"));
		dataList.add(ExcelParseUtil.toEntity("receivablesNoInvoice", "The money has been collected but not invoiced (yuan)"));
		dataList.add(ExcelParseUtil.toEntity("invoiceNoReceivables", "Invoiced but not paid back (yuan)"));
		ExcelParseUtil.exportExcel(recordList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Invoice Statistics";
			}
		}, dataList);
	}
}
