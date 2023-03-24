package com.megazone.crm.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.crm.entity.BO.CrmBackLogBO;
import com.megazone.crm.entity.PO.CrmReceivablesPlan;
import com.megazone.crm.service.ICrmBackLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-23
 */
@RestController
@RequestMapping("/crmBackLog")
@Api(tags = "")
public class CrmBackLogController {

	@Autowired
	private ICrmBackLogService crmBackLogService;


	@PostMapping("/num")
	@ApiOperation("")
	public Result<JsonNode> num() {
		JSONObject num = crmBackLogService.num();
		return Result.ok(num.node);
	}

	@PostMapping("/todayLeads")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> todayLeads(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<Map<String, Object>> basePage = crmBackLogService.todayLeads(crmBackLogBO);
		return Result.ok(basePage);
	}

	@PostMapping("/todayCustomer")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> todayCustomer(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<Map<String, Object>> basePage = crmBackLogService.todayCustomer(crmBackLogBO);
		return Result.ok(basePage);
	}

	@PostMapping("/todayBusiness")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> todayBusiness(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<Map<String, Object>> basePage = crmBackLogService.todayBusiness(crmBackLogBO);
		return Result.ok(basePage);
	}

	@PostMapping("/followLeads")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> followLeads(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<Map<String, Object>> basePage = crmBackLogService.followLeads(crmBackLogBO);
		return Result.ok(basePage);
	}

	@PostMapping("/followCustomer")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> followCustomer(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<Map<String, Object>> basePage = crmBackLogService.followCustomer(crmBackLogBO);
		return Result.ok(basePage);
	}

	@PostMapping("/returnVisitRemind")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> returnVisitRemind(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<Map<String, Object>> basePage = crmBackLogService.returnVisitRemind(crmBackLogBO);
		return Result.ok(basePage);
	}

	@PostMapping("/setLeadsFollowup")
	@ApiOperation("")
	public Result setLeadsFollowup(@RequestBody List<Integer> ids) {
		crmBackLogService.setLeadsFollowup(ids);
		return Result.ok();
	}

	@PostMapping("/setCustomerFollowup")
	@ApiOperation("")
	public Result setCustomerFollowup(@RequestBody List<Integer> ids) {
		crmBackLogService.setCustomerFollowup(ids);
		return Result.ok();
	}

	@PostMapping("/checkContract")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> checkContract(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<Map<String, Object>> basePage = crmBackLogService.checkContract(crmBackLogBO);
		return Result.ok(basePage);
	}

	@PostMapping("/checkReceivables")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> checkReceivables(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<Map<String, Object>> basePage = crmBackLogService.checkReceivables(crmBackLogBO);
		return Result.ok(basePage);
	}

	@PostMapping("/checkInvoice")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> checkInvoice(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<Map<String, Object>> basePage = crmBackLogService.checkInvoice(crmBackLogBO);
		return Result.ok(basePage);
	}

	@PostMapping("/remindReceivables")
	@ApiOperation("")
	public Result<BasePage<CrmReceivablesPlan>> remindReceivables(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<CrmReceivablesPlan> basePage = crmBackLogService.remindReceivables(crmBackLogBO);
		return Result.ok(basePage);
	}

	@PostMapping("/endContract")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> endContract(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<Map<String, Object>> basePage = crmBackLogService.endContract(crmBackLogBO);
		return Result.ok(basePage);
	}

	@PostMapping("/allDeal")
	@ApiOperation("")
	public Result allDeal(@RequestParam("model") Integer model) {
		crmBackLogService.allDeal(model);
		return Result.ok();
	}

	/**
	 *
	 */
	@PostMapping("/dealById")
	@ApiOperation("")
	public Result dealById(@RequestBody JSONObject jsonObject) {
		Integer model = jsonObject.getInteger("model");
		List<JSONObject> jsonObjectList = jsonObject.getJSONArray("list").toJavaList(JSONObject.class);
		crmBackLogService.dealById(model, jsonObjectList);
		return Result.ok();
	}

	/**
	 * @author wyq
	 */
	@PostMapping("/putInPoolRemind")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> putInPoolRemind(@RequestBody CrmBackLogBO crmBackLogBO) {
		BasePage<Map<String, Object>> page = crmBackLogService.putInPoolRemind(crmBackLogBO);
		return Result.ok(page);
	}

}

