package com.megazone.core.feign.crm.service;

import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(name = "crm", contextId = "crmEvent")
public interface CrmEventService {

	@PostMapping("/crmEvent/endContract")
	@ApiExplain("")
	Result<List<String>> endContract(@RequestBody CrmEventBO crmEventBO);

	@PostMapping("/crmEvent/eventCustomer")
	@ApiExplain("")
	Result<List<String>> eventCustomer(@RequestBody CrmEventBO crmEventBO);

	@PostMapping("/crmEvent/eventLeads")
	@ApiExplain("")
	Result<List<String>> eventLeads(@RequestBody CrmEventBO crmEventBO);

	@PostMapping("/crmEvent/eventBusiness")
	@ApiExplain("")
	Result<List<String>> eventBusiness(@RequestBody CrmEventBO crmEventBO);

	@PostMapping("/crmEvent/eventDealBusiness")
	@ApiExplain("")
	Result<List<String>> eventDealBusiness(@RequestBody CrmEventBO crmEventBO);

	@PostMapping("/crmEvent/receiveContract")
	@ApiExplain("")
	Result<List<String>> receiveContract(@RequestBody CrmEventBO crmEventBO);

	@PostMapping("/crmEvent/eventCustomerPageList")
	@ApiExplain("")
	Result<BasePage<Map<String, Object>>> eventCustomerPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO);

	@PostMapping("/crmEvent/eventLeadsPageList")
	@ApiExplain("")
	Result<BasePage<Map<String, Object>>> eventLeadsPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO);

	@PostMapping("/crmEvent/eventBusinessPageList")
	@ApiExplain("")
	Result<BasePage<Map<String, Object>>> eventBusinessPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO);

	@PostMapping("/crmEvent/eventDealBusinessPageList")
	@ApiExplain("")
	Result<BasePage<Map<String, Object>>> eventDealBusinessPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO);

	@PostMapping("/crmEvent/eventContractPageList")
	@ApiExplain("")
	Result<BasePage<Map<String, Object>>> eventContractPageList(@RequestBody QueryEventCrmPageBO eventCrmPageBO);
}
