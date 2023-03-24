package com.megazone.bi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.bi.entity.VO.BiPageVO;
import com.megazone.bi.entity.VO.BiParamVO;
import com.megazone.bi.service.BiWorkService;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/biWork")
@Api(tags = "Office Analysis Interface")
@Slf4j
public class BiWorkController {

	@Autowired
	private BiWorkService biWorkService;


	@ApiOperation("Query log statistics")
	@PostMapping("/logStatistics")
	public Result<List<JSONObject>> logStatistics(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biWorkService.logStatistics(biParams);
		return R.ok(objectList);
	}


	@ApiOperation("Query log statistics export")
	@PostMapping("/logStatisticsExport")
	public void logStatisticsExport(@RequestBody BiParams biParams) throws IOException {
		List<JSONObject> recordList = biWorkService.logStatistics(biParams);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("realname", "employee"));
		dataList.add(ExcelParseUtil.toEntity("count", "Number of fills"));
		dataList.add(ExcelParseUtil.toEntity("unCommentCount", "uncomment count"));
		dataList.add(ExcelParseUtil.toEntity("commentCount", "Number of comments"));
		ExcelParseUtil.exportExcel(recordList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Log Statistics";
			}
		}, dataList);
	}

	@ApiOperation("Query approval statistics")
	@PostMapping("/examineStatistics")
	public Result<JsonNode> examineStatistics(@RequestBody BiParams biParams) {
		JSONObject object = biWorkService.examineStatistics(biParams);
		return R.ok(object.node);
	}


	@ApiOperation("Query approval statistics export")
	@PostMapping("/examineStatisticsExport")
	public void examineStatisticsExport(@RequestBody BiParams biParams) throws IOException {
		JSONObject object = biWorkService.examineStatistics(biParams);
		List<JSONObject> userList = object.getJSONArray("userList").toJavaList(JSONObject.class);
		List<JSONObject> categoryList = object.getJSONArray("categoryList").toJavaList(JSONObject.class);
		userList.forEach(user -> {
			user.remove("img");
			user.remove("userId");
			user.remove("username");
		});
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("realname", "employee"));
		for (JSONObject record : categoryList) {
			dataList.add(ExcelParseUtil.toEntity("count_" + record.getInteger("categoryId"), record.getString("title")));
		}
		ExcelParseUtil.exportExcel(userList.stream().map(JSONObject::getInnerMapObject).collect(Collectors.toList()), new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Approval Analysis";
			}
		}, dataList);
	}


	@ApiOperation("Query approval details")
	@PostMapping("/examineInfo")
	public Result<BiPageVO<JSONObject>> examineInfo(@RequestBody BiParamVO biParamVO) {
		return R.ok(biWorkService.examineInfo(biParamVO));
	}
}
