package com.megazone.bi.controller;

import com.megazone.bi.entity.VO.ProductStatisticsVO;
import com.megazone.bi.service.BiService;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.utils.ExcelParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bi")
@Api(tags = "Business Intelligence Interface")
@Slf4j
public class BiController {

	@Autowired
	private BiService biService;


	@PostMapping("/productStatistics")
	@ApiOperation("Product Sales Statistics")
	public Result<BasePage<ProductStatisticsVO>> productStatistics(@RequestBody BiParams biParams) {
		BasePage<ProductStatisticsVO> objectList = biService.queryProductSell(biParams);
		return Result.ok(objectList);
	}

	@PostMapping("/productStatisticsExport")
	@ApiOperation("Product sales statistics export")
	public void productStatisticsExport(@RequestBody BiParams biParams) {
		List<Map<String, Object>> objectList = biService.productSellExport(biParams);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("categoryName", "Product Category"));
		dataList.add(ExcelParseUtil.toEntity("productName", "Product Name"));
		dataList.add(ExcelParseUtil.toEntity("contractNum", "ContractNumber"));
		dataList.add(ExcelParseUtil.toEntity("num", "Total Quantity"));
		dataList.add(ExcelParseUtil.toEntity("total", "Order Product Subtotal"));
		ExcelParseUtil.exportExcel(objectList, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Product sales";
			}
		}, dataList);
	}

	@PostMapping("/taskCompleteStatistics")
	@ApiOperation("Get the completion of business intelligence performance goals")
	public Result<List<JSONObject>> taskCompleteStatistics(@RequestParam("year") String year, @RequestParam("type") Integer type, Integer deptId, Long userId, Integer isUser) {
		List<JSONObject> objectList = biService.taskCompleteStatistics(year, type, deptId, userId, isUser);
		return Result.ok(objectList);
	}

	@PostMapping("/taskCompleteStatisticsExport")
	@ApiOperation("Get the export of business intelligence performance target completion")
	public void taskCompleteStatisticsExport(@RequestParam("year") String year, @RequestParam("type") Integer type, Integer deptId, Long userId, Integer isUser) {
		List<Map<String, Object>> list = biService.taskCompleteStatisticsExport(year, type, deptId, userId, isUser);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("name", "name"));
		dataList.add(ExcelParseUtil.toEntity("month", "month"));
		dataList.add(ExcelParseUtil.toEntity("achievement", "target"));
		dataList.add(ExcelParseUtil.toEntity("money", "Done"));
		dataList.add(ExcelParseUtil.toEntity("rate", "Completion rate"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Completion of performance goals";
			}
		}, dataList);
	}
}
