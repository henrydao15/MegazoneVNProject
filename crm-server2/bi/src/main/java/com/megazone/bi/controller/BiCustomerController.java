package com.megazone.bi.controller;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.bi.mapper.BiCustomerMapper;
import com.megazone.bi.service.BiCustomerService;
import com.megazone.core.common.Const;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.servlet.ApplicationContextHolder;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("biCustomer")
@Api(tags = "Customer Business Intelligence Module")
@Slf4j
public class BiCustomerController {

	@Autowired
	private BiCustomerService biCustomerService;

	@ApiOperation("Customer Total Analysis")
	@PostMapping("/totalCustomerStats")
	public Result<List<JSONObject>> totalCustomerStats(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biCustomerService.totalCustomerStats(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Customer Total Analysis Chart")
	@PostMapping("/totalCustomerTable")
	public Result<JsonNode> totalCustomerTable(@RequestBody BiParams biParams) {
		JSONObject object = biCustomerService.totalCustomerTable(biParams);
		return Result.ok(object.node);
	}

	@ApiOperation("Export of total customer analysis graph")
	@PostMapping("/totalCustomerTableExport")
	public void totalCustomerTableExport(@RequestBody BiParams biParams) {
		JSONObject kv = biCustomerService.totalCustomerTable(biParams);
		List<JSONObject> recordList = kv.getJSONArray("list").toJavaList(JSONObject.class);
		List<Map<String, Object>> list = recordList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList());
		JSONObject total = kv.getJSONObject("total");
		total.put("dealCustomerRate", "");
		list.add(total.getInnerMapObject());
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("realname", "employee name"));
		dataList.add(ExcelParseUtil.toEntity("customerNum", "Number of new customers"));
		dataList.add(ExcelParseUtil.toEntity("dealCustomerNum", "Number of deal customers"));
		dataList.add(ExcelParseUtil.toEntity("dealCustomerRate", "Customer transaction rate"));
		dataList.add(ExcelParseUtil.toEntity("contractMoney", "Total Contract Amount"));
		dataList.add(ExcelParseUtil.toEntity("receivablesMoney", "Receivable Amount"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Customer total analysis";
			}
		}, dataList);
	}

	@ApiOperation("Analysis of customer follow-up times")
	@PostMapping("/customerRecordStats")
	public Result<List<JSONObject>> customerRecordStats(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biCustomerService.customerRecordStats(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Customer follow-up times analysis table")
	@PostMapping("/customerRecordInfo")
	public Result<JsonNode> customerRecordInfo(@RequestBody BiParams biParams) {
		JSONObject jsonObject = biCustomerService.customerRecordInfo(biParams);
		return Result.ok(jsonObject.node);
	}

	@ApiOperation("Customer follow-up times analysis table export")
	@PostMapping("/customerRecordInfoExport")
	public void customerRecordInfoExport(@RequestBody BiParams biParams) {
		JSONObject kv = biCustomerService.customerRecordInfo(biParams);
		List<JSONObject> recordList = kv.getJSONArray("list").toJavaList(JSONObject.class);
		List<Map<String, Object>> list = recordList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList());
		JSONObject total = kv.getJSONObject("total");
		list.add(total.getInnerMapObject());
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("realname", "employee name"));
		dataList.add(ExcelParseUtil.toEntity("recordCount", "follow-up count"));
		dataList.add(ExcelParseUtil.toEntity("customerCount", "Number of follow-up customers"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Analysis of customer follow-up times";
			}
		}, dataList);
	}

	@ApiOperation("Analysis of customer follow-up methods")
	@PostMapping("/customerRecodCategoryStats")
	public Result<List<JSONObject>> customerRecodCategoryStats(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biCustomerService.customerRecodCategoryStats(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Customer follow-up analysis and export")
	@PostMapping("/customerRecodCategoryStatsExport")
	public void customerRecodCategoryStatsExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biCustomerService.customerRecodCategoryStats(biParams);
		List<Map<String, Object>> list = objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList());
		JSONObject total = new JSONObject();
		Long totalRecordNum = objectList.stream().collect(Collectors.summarizingInt(r -> r.getInteger("recordNum"))).getSum();
		total.fluentPut("category", "total").fluentPut("recordNum", totalRecordNum).fluentPut("proportion", "100%");
		list.add(total.getInnerMapObject());
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("category", "Follow-up method"));
		dataList.add(ExcelParseUtil.toEntity("recordNum", "Number"));
		dataList.add(ExcelParseUtil.toEntity("proportion", "Proportion(%)"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Customer follow-up analysis";
			}
		}, dataList);
	}


	@ApiOperation("Customer Conversion Rate Analysis Chart")
	@PostMapping("/customerConversionStats")
	public Result<List<JSONObject>> customerConversionStats(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biCustomerService.customerConversionStats(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Customer Conversion Rate Analysis Table")
	@PostMapping("/customerConversionInfo")
	public Result<BasePage<JSONObject>> customerConversionInfo(@RequestBody BiParams basePageRequest) {
		BasePage<JSONObject> basePage = biCustomerService.customerConversionInfo(basePageRequest);
		return Result.ok(basePage);
	}


	@ApiOperation("High Seas Customer Analysis Chart")
	@PostMapping("/poolStats")
	public Result<List<JSONObject>> poolStats(@RequestBody BiParams biParams) {
		List<JSONObject> basePage = biCustomerService.poolStats(biParams);
		return Result.ok(basePage);
	}

	@ApiOperation("High seas customer analysis table")
	@PostMapping("/poolTable")
	public Result<JsonNode> poolTable(@RequestBody BiParams biParams) {
		JSONObject basePage = biCustomerService.poolTable(biParams);
		return Result.ok(basePage.node);
	}

	@ApiOperation("Export of high seas customer analysis table")
	@PostMapping("/poolTableExport")
	public void poolTableExport(@RequestBody BiParams biParams) {
		JSONObject kv = biCustomerService.poolTable(biParams);
		List<JSONObject> recordList = kv.getJSONArray("list").toJavaList(JSONObject.class);
		List<Map<String, Object>> list = recordList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList());
		JSONObject total = kv.getJSONObject("total");
		list.add(total.getInnerMapObject());

		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("realname", "name"));
		dataList.add(ExcelParseUtil.toEntity("deptName", "Dept"));
		dataList.add(ExcelParseUtil.toEntity("receiveNum", "Number of customers received from the Ocean Pool"));
		dataList.add(ExcelParseUtil.toEntity("putInNum", "Number of customers entering the high seas"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "High seas customer analysis";
			}
		}, dataList);
	}

	@ApiOperation("Employee customer transaction cycle diagram")
	@PostMapping("/employeeCycle")
	public Result<List<JSONObject>> employeeCycle(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biCustomerService.employeeCycle(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Employee Customer Transaction Periodic Table")
	@PostMapping("/employeeCycleInfo")
	public Result<JsonNode> employeeCycleInfo(@RequestBody BiParams biParams) {
		JSONObject jsonObject = biCustomerService.employeeCycleInfo(biParams);
		return Result.ok(jsonObject.node);
	}

	@ApiOperation("Employee customer transaction periodic table export")
	@PostMapping("/employeeCycleInfoExport")
	public void employeeCycleInfoExport(@RequestBody BiParams biParams) {
		JSONObject kv = biCustomerService.employeeCycleInfo(biParams);
		List<JSONObject> recordList = kv.getJSONArray("list").toJavaList(JSONObject.class);
		List<Map<String, Object>> list = recordList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList());
		JSONObject total = kv.getJSONObject("total");
		list.add(total.getInnerMapObject());
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("realname", "name"));
		dataList.add(ExcelParseUtil.toEntity("cycle", "Deal cycle (days)"));
		dataList.add(ExcelParseUtil.toEntity("customerNum", "Number of transaction customers"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Employee customer transaction cycle analysis";
			}
		}, dataList);
	}


	@ApiOperation("Regional transaction cycle chart")
	@PostMapping("/districtCycle")
	public Result<JsonNode> districtCycle(@RequestBody BiParams biParams) {
		JSONObject jsonObject = biCustomerService.districtCycle(biParams);
		return Result.ok(jsonObject.node);
	}

	@ApiOperation("Export of regional transaction cycle chart")
	@PostMapping("/districtCycleExport")
	public void districtCycleExport(@RequestBody BiParams biParams) {
		JSONObject kv = biCustomerService.districtCycle(biParams);
		List<JSONObject> recordList = kv.getJSONArray("list").toJavaList(JSONObject.class);
		List<Map<String, Object>> list = recordList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList());
		JSONObject total = kv.getJSONObject("total");
		list.add(total.getInnerMapObject());
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("type", "region"));
		dataList.add(ExcelParseUtil.toEntity("cycle", "Deal cycle (days)"));
		dataList.add(ExcelParseUtil.toEntity("customerNum", "Number of transaction customers"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Analysis of regional transaction cycle";
			}
		}, dataList);
	}

	@ApiOperation("Product transaction cycle")
	@PostMapping("/productCycle")
	public Result<JsonNode> productCycle(@RequestBody BiParams biParams) {
		JSONObject jsonObject = biCustomerService.productCycle(biParams);
		return Result.ok(jsonObject.node);
	}

	@ApiOperation("Product transaction cycle export")
	@PostMapping("/productCycleExport")
	public void productCycleExport(@RequestBody BiParams biParams) {
		JSONObject kv = biCustomerService.productCycle(biParams);
		List<JSONObject> recordList = kv.getJSONArray("list").toJavaList(JSONObject.class);
		List<Map<String, Object>> list = recordList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList());
		JSONObject total = kv.getJSONObject("total");
		list.add(total.getInnerMapObject());

		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("productName", "Product Name"));
		dataList.add(ExcelParseUtil.toEntity("cycle", "Deal cycle (days)"));
		dataList.add(ExcelParseUtil.toEntity("customerNum", "Number of transaction customers"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Product transaction cycle analysis";
			}
		}, dataList);
	}

	@ApiOperation("Customer Satisfaction Analysis")
	@PostMapping("/customerSatisfactionTable")
	public Result<List<JSONObject>> customerSatisfactionTable(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biCustomerService.customerSatisfactionTable(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Customer Satisfaction Analysis Export")
	@PostMapping("/customerSatisfactionExport")
	public void customerSatisfactionExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biCustomerService.customerSatisfactionTable(biParams);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		JSONObject object = ApplicationContextHolder.getBean(BiCustomerMapper.class).querySatisfactionOptionList();
		List<String> optionList = StrUtil.splitTrim(object.getString("options"), Const.SEPARATOR);
		dataList.add(ExcelParseUtil.toEntity("realname", "employee name"));
		dataList.add(ExcelParseUtil.toEntity("visitContractNum", "Total number of return visit contracts"));
		optionList.forEach(option -> dataList.add(ExcelParseUtil.toEntity(option, option)));
		ExcelParseUtil.exportExcel(objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Customer Satisfaction Analysis";
			}
		}, dataList);
	}

	@ApiOperation("Product Satisfaction Analysis")
	@PostMapping("/productSatisfactionTable")
	public Result<List<JSONObject>> productSatisfactionTable(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biCustomerService.productSatisfactionTable(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Product Satisfaction Analysis Export")
	@PostMapping("/productSatisfactionExport")
	public void productSatisfactionExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biCustomerService.productSatisfactionTable(biParams);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		JSONObject object = ApplicationContextHolder.getBean(BiCustomerMapper.class).querySatisfactionOptionList();
		List<String> optionList = StrUtil.splitTrim(object.getString("options"), Const.SEPARATOR);
		dataList.add(ExcelParseUtil.toEntity("productName", "Product Name"));
		dataList.add(ExcelParseUtil.toEntity("visitContractNum", "Total number of return visit contracts"));
		optionList.forEach(option -> dataList.add(ExcelParseUtil.toEntity(option, option)));
		ExcelParseUtil.exportExcel(objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Product Satisfaction Analysis";
			}
		}, dataList);
	}
}
