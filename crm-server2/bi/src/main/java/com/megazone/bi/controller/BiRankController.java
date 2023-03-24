package com.megazone.bi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.bi.service.BiRankService;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.R;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/biRanking")
@Api(tags = "Business Intelligence Leaderboard Interface")
@Slf4j
public class BiRankController {

	@Autowired
	private BiRankService biRankService;

	@ApiOperation("City Distribution Analysis")
	@PostMapping("/addressAnalyse")
	public Result<List<JSONObject>> addressAnalyse(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.addressAnalyse(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Customer Industry Analysis")
	@PostMapping("/portrait")
	public Result<List<JSONObject>> portrait(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.portrait(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Customer level analysis")
	@PostMapping("/portraitLevel")
	public Result<List<JSONObject>> portraitLevel(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.portraitLevel(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Customer Source Analysis")
	@PostMapping("/portraitSource")
	public Result<List<JSONObject>> portraitSource(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.portraitSource(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Product Category Sales Analysis")
	@PostMapping("/contractProductRanKing")
	public Result<List<JSONObject>> contractProductRanKing(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.contractProductRanKing(biParams);
		return Result.ok(objectList);
	}


	@ApiOperation("Contract Amount Ranking")
	@PostMapping("/contractRanKing")
	public Result<List<JSONObject>> contractRanKing(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.contractRanKing(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Contract Amount List Export")
	@PostMapping("/contractRanKingExport")
	public void contractRanKingExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.contractRanKing(biParams);
		for (int i = 0; i < objectList.size(); i++) {
			objectList.get(i).put("order", i + 1);
		}
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("order", "Company Total Rank"));
		dataList.add(ExcelParseUtil.toEntity("realname", "signator"));
		dataList.add(ExcelParseUtil.toEntity("structureName", "Department"));
		dataList.add(ExcelParseUtil.toEntity("money", "Contract amount (yuan)"));
		ExcelParseUtil.exportExcel(objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Contract Amount Leaderboard";
			}
		}, dataList);
	}

	@ApiOperation("Repayment Amount Ranking List")
	@PostMapping("/receivablesRanKing")
	public Result<List<JSONObject>> receivablesRanKing(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.receivablesRanKing(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Export the list of payment amounts")
	@PostMapping("/receivablesRanKingExport")
	public void receivablesRanKingExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.receivablesRanKing(biParams);
		for (int i = 0; i < objectList.size(); i++) {
			objectList.get(i).put("order", i + 1);
		}
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("order", "Company Total Rank"));
		dataList.add(ExcelParseUtil.toEntity("realname", "signator"));
		dataList.add(ExcelParseUtil.toEntity("structureName", "Department"));
		dataList.add(ExcelParseUtil.toEntity("money", "Return amount (yuan)"));
		ExcelParseUtil.exportExcel(objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Repayment Amount Ranking";
			}
		}, dataList);
	}

	@ApiOperation("Signed Contract Leaderboard")
	@PostMapping("/contractCountRanKing")
	public Result<List<JSONObject>> contractCountRanKing(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.contractCountRanKing(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Export of contract ranking list")
	@PostMapping("/contractCountRanKingExport")
	public void contractCountRanKingExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.contractCountRanKing(biParams);
		for (int i = 0; i < objectList.size(); i++) {
			objectList.get(i).put("order", i + 1);
		}
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("order", "Company Total Rank"));
		dataList.add(ExcelParseUtil.toEntity("realname", "signator"));
		dataList.add(ExcelParseUtil.toEntity("structureName", "Department"));
		dataList.add(ExcelParseUtil.toEntity("count", "Number of contracts signed (number)"));
		ExcelParseUtil.exportExcel(objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Signed contract ranking";
			}
		}, dataList);
	}


	@ApiOperation("Product Sales Ranking")
	@PostMapping("/productCountRanKing")
	public Result<List<JSONObject>> productCountRanKing(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.productCountRanKing(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Product Sales Ranking Export")
	@PostMapping("/productCountRanKingExport")
	public void productCountRanKingExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.productCountRanKing(biParams);
		for (int i = 0; i < objectList.size(); i++) {
			objectList.get(i).put("order", i + 1);
		}
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("order", "Company Total Rank"));
		dataList.add(ExcelParseUtil.toEntity("realname", "signator"));
		dataList.add(ExcelParseUtil.toEntity("structureName", "Department"));
		dataList.add(ExcelParseUtil.toEntity("count", "Product Sales"));
		ExcelParseUtil.exportExcel(objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Product sales ranking";
			}
		}, dataList);
	}

	@ApiOperation("New Customer Number Ranking List")
	@PostMapping("/customerCountRanKing")
	public Result<List<JSONObject>> customerCountRanKing(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.customerCountRanKing(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Export the list of new customers")
	@PostMapping("/customerCountRanKingExport")
	public void customerCountRanKingExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.customerCountRanKing(biParams);
		for (int i = 0; i < objectList.size(); i++) {
			objectList.get(i).put("order", i + 1);
		}
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("order", "Company Total Rank"));
		dataList.add(ExcelParseUtil.toEntity("realname", "Creator"));
		dataList.add(ExcelParseUtil.toEntity("structureName", "Department"));
		dataList.add(ExcelParseUtil.toEntity("count", "Number of new customers (number)"));
		ExcelParseUtil.exportExcel(objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Ranking of new customers";
			}
		}, dataList);
	}

	@PostMapping("/ranking")
	@ApiOperation("Leaderboard")
	public Result<JsonNode> ranking(@RequestBody BiParams biParams) {
		JSONObject jsonObject = biRankService.ranking(biParams);
		return R.ok(jsonObject.node);
	}

	@ApiOperation("Add Contact Leaderboard")
	@PostMapping("/contactsCountRanKing")
	public Result<List<JSONObject>> contactsCountRanKing(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.contactsCountRanKing(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Add contact list export")
	@PostMapping("/contactsCountRanKingExport")
	public void contactsCountRanKingExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.contactsCountRanKing(biParams);
		for (int i = 0; i < objectList.size(); i++) {
			objectList.get(i).put("order", i + 1);
		}
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("order", "Company Total Rank"));
		dataList.add(ExcelParseUtil.toEntity("realname", "Creator"));
		dataList.add(ExcelParseUtil.toEntity("structureName", "Department"));
		dataList.add(ExcelParseUtil.toEntity("count", "Number of new contacts (number)"));
		ExcelParseUtil.exportExcel(objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "New contact ranking";
			}
		}, dataList);
	}


	@ApiOperation("Follow-up customer rankings")
	@PostMapping("/customerGenjinCountRanKing")
	public Result<List<JSONObject>> customerGenjinCountRanKing(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.customerGenjinCountRanKing(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Follow up the number of customers to export the leaderboard")
	@PostMapping("/customerGenjinCountRanKingExport")
	public void customerGenjinCountRanKingExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.customerGenjinCountRanKing(biParams);
		for (int i = 0; i < objectList.size(); i++) {
			objectList.get(i).put("order", i + 1);
		}
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("order", "Company Total Rank"));
		dataList.add(ExcelParseUtil.toEntity("realname", "employee"));
		dataList.add(ExcelParseUtil.toEntity("structureName", "Department"));
		dataList.add(ExcelParseUtil.toEntity("count", "Number of follow-up customers (number)"));
		ExcelParseUtil.exportExcel(objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Follow-up customer ranking";
			}
		}, dataList);
	}


	@ApiOperation("Follow-up Times Ranking List")
	@PostMapping("/recordCountRanKing")
	public Result<List<JSONObject>> recordCountRanKing(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.recordCountRanKing(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Follow up leaderboard export")
	@PostMapping("/recordCountRanKingExport")
	public void recordCountRanKingExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.recordCountRanKing(biParams);
		for (int i = 0; i < objectList.size(); i++) {
			objectList.get(i).put("order", i + 1);
		}

		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("order", "Company Total Rank"));
		dataList.add(ExcelParseUtil.toEntity("realname", "employee"));
		dataList.add(ExcelParseUtil.toEntity("structureName", "Department"));
		dataList.add(ExcelParseUtil.toEntity("count", "Number of follow-up customers (number)"));
		ExcelParseUtil.exportExcel(objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Follow-up times ranking";
			}
		}, dataList);
	}

	@ApiOperation("Number of business trips")
	@PostMapping("/travelCountRanKing")
	public Result<List<JSONObject>> travelCountRanKing(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.travelCountRanKing(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Export the number of business trips")
	@PostMapping("/travelCountRanKingExport")
	public void travelCountRanKingExport(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biRankService.travelCountRanKing(biParams);
		for (int i = 0; i < objectList.size(); i++) {
			objectList.get(i).put("order", i + 1);
		}
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("order", "Company Total Rank"));
		dataList.add(ExcelParseUtil.toEntity("realname", "employee"));
		dataList.add(ExcelParseUtil.toEntity("structureName", "Department"));
		dataList.add(ExcelParseUtil.toEntity("count", "Number of business trips (times)"));
		ExcelParseUtil.exportExcel(objectList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Number of business trips";
			}
		}, dataList);
	}

}
