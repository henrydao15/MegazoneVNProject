package com.megazone.bi.controller;

import com.megazone.bi.service.BiFunnelService;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.BiParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/biFunnel")
@Api(tags = "Sales Funnel Analysis")
@Slf4j
public class BiFunnelController {

	@Autowired
	private BiFunnelService biFunnelService;


	@ApiOperation("Sales Funnel")
	@PostMapping("/sellFunnel")
	public Result<List<JSONObject>> sellFunnel(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biFunnelService.sellFunnel(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Add business opportunity analysis chart")
	@PostMapping("/addBusinessAnalyze")
	public Result<List<JSONObject>> addBusinessAnalyze(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biFunnelService.addBusinessAnalyze(biParams);
		return Result.ok(objectList);
	}

	@ApiOperation("Add business opportunity analysis table")
	@PostMapping("/sellFunnelList")
	public Result<BasePage<JSONObject>> sellFunnelList(@RequestBody BiParams biParams) {
		BasePage<JSONObject> basePage = biFunnelService.sellFunnelList(biParams);
		return Result.ok(basePage);
	}

	@ApiOperation("Opportunity Conversion Rate Analysis")
	@PostMapping("/win")
	public Result<List<JSONObject>> win(@RequestBody BiParams biParams) {
		List<JSONObject> objectList = biFunnelService.win(biParams);
		return Result.ok(objectList);
	}
}
