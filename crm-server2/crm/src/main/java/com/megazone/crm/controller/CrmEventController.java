package com.megazone.crm.controller;

import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import com.megazone.crm.service.ICrmBusinessService;
import com.megazone.crm.service.ICrmContractService;
import com.megazone.crm.service.ICrmCustomerService;
import com.megazone.crm.service.ICrmLeadsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crmEvent")
@Api(value = "oa")
public class CrmEventController {


	@Autowired
	private ICrmContractService crmContractService;

	@Autowired
	private ICrmCustomerService crmCustomerService;

	@Autowired
	private ICrmLeadsService leadsService;

	@Autowired
	private ICrmBusinessService crmBusinessService;

	@PostMapping("/endContract")
	@ApiExplain("")
	public Result<List<String>> endContract(@RequestBody CrmEventBO crmEventBO) {
		List<String> list = crmContractService.endContract(crmEventBO);
		return Result.ok(list);
	}

	@PostMapping("/eventCustomer")
	@ApiExplain("")
	public Result<List<String>> eventCustomer(@RequestBody CrmEventBO crmEventBO) {
		List<String> list = crmCustomerService.eventCustomer(crmEventBO);
		return Result.ok(list);
	}

	@PostMapping("/eventLeads")
	@ApiExplain("")
	public Result<List<String>> eventLeads(@RequestBody CrmEventBO crmEventBO) {
		List<String> list = leadsService.eventLeads(crmEventBO);
		return Result.ok(list);
	}

	@PostMapping("/eventBusiness")
	@ApiExplain("")
	public Result<List<String>> eventBusiness(@RequestBody CrmEventBO crmEventBO) {
		List<String> list = crmBusinessService.eventBusiness(crmEventBO);
		return Result.ok(list);
	}

	@PostMapping("/eventDealBusiness")
	@ApiExplain("")
	public Result<List<String>> eventDealBusiness(@RequestBody CrmEventBO crmEventBO) {
		List<String> list = crmBusinessService.eventDealBusiness(crmEventBO);
		return Result.ok(list);
	}

	@PostMapping("/receiveContract")
	@ApiExplain("")
	public Result<List<String>> receiveContract(@RequestBody CrmEventBO crmEventBO) {
		List<String> list = crmContractService.receiveContract(crmEventBO);
		return Result.ok(list);
	}

	@PostMapping("/eventCustomerPageList")
	@ApiExplain("")
	public Result<BasePage<Map<String, Object>>> eventCustomerPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO) {
		BasePage<Map<String, Object>> page = crmCustomerService.eventCustomerPageList(eventCrmPageBO);
		return Result.ok(page);
	}

	@PostMapping("/eventLeadsPageList")
	@ApiExplain("")
	public Result<BasePage<Map<String, Object>>> eventLeadsPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO) {
		BasePage<Map<String, Object>> page = leadsService.eventLeadsPageList(eventCrmPageBO);
		return Result.ok(page);
	}

	@PostMapping("/eventBusinessPageList")
	@ApiExplain("")
	public Result<BasePage<Map<String, Object>>> eventBusinessPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO) {
		BasePage<Map<String, Object>> page = crmBusinessService.eventBusinessPageList(eventCrmPageBO);
		return Result.ok(page);
	}

	@PostMapping("/eventDealBusinessPageList")
	@ApiExplain("")
	public Result<BasePage<Map<String, Object>>> eventDealBusinessPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO) {
		BasePage<Map<String, Object>> page = crmBusinessService.eventDealBusinessPageList(eventCrmPageBO);
		return Result.ok(page);
	}

	/**
	 * type = 1
	 * type = 2
	 */
	@PostMapping("/eventContractPageList")
	@ApiExplain("")
	public Result<BasePage<Map<String, Object>>> eventContractPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO) {
		BasePage<Map<String, Object>> page = crmContractService.eventContractPageList(eventCrmPageBO);
		return Result.ok(page);
	}


}
