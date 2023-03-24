package com.megazone.core.feign.crm.service;

import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.CrmSearchBO;
import com.megazone.core.feign.crm.entity.ExamineField;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.feign.crm.service.impl.CrmServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@FeignClient(name = "crm", contextId = "", fallback = CrmServiceImpl.class)
public interface CrmService {

	/**
	 * @param ids ids
	 * @return entity
	 */
	@PostMapping("/crmCustomer/querySimpleEntity")
	public Result<List<SimpleCrmEntity>> queryCustomerInfo(@RequestBody Collection ids);

	@PostMapping("/crmCustomer/queryCustomerName")
	Result<String> queryCustomerName(@RequestParam("customerId") Integer customerId);

	@PostMapping("/crmLeads/querySimpleEntity")
	public Result<List<SimpleCrmEntity>> queryLeadsInfo(@RequestBody Collection ids);

	@PostMapping("/crmInvoice/querySimpleEntity")
	public Result<List<SimpleCrmEntity>> queryInvoiceInfo(@RequestBody Collection ids);

	@PostMapping("/crmReceivables/querySimpleEntity")
	public Result<List<SimpleCrmEntity>> queryReceivablesInfo(@RequestBody Collection ids);

	@PostMapping("/crmReturnVisit/querySimpleEntity")
	public Result<List<SimpleCrmEntity>> queryReturnVisitInfo(@RequestBody Collection ids);

	/**
	 * @param name
	 * @return entity
	 */
	@PostMapping("/crmCustomer/queryByNameCustomerInfo")
	public Result<List<SimpleCrmEntity>> queryByNameCustomerInfo(@RequestParam("name") String name);


	/**
	 * @param name
	 * @return entity
	 */
	@PostMapping("/crmCustomer/queryNameCustomerInfo")
	public Result<List<SimpleCrmEntity>> queryNameCustomerInfo(@RequestParam("name") String name);

	/**
	 * @param ids ids
	 * @return entity
	 */
	@PostMapping("/crmContacts/querySimpleEntity")
	public Result<List<SimpleCrmEntity>> queryContactsInfo(@RequestBody Collection ids);

	/**
	 * @param ids ids
	 * @return entity
	 */
	@PostMapping("/crmBusiness/querySimpleEntity")
	public Result<List<SimpleCrmEntity>> queryBusinessInfo(@RequestBody Collection ids);

	/**
	 * @param ids ids
	 * @return entity
	 */
	@PostMapping("/crmContract/querySimpleEntity")
	public Result<List<SimpleCrmEntity>> queryContractInfo(@RequestBody Collection ids);

	@PostMapping("/crmProduct/querySimpleEntity")
	public Result<List<SimpleCrmEntity>> queryProductInfo();

	/**
	 * @param type
	 * @param activityType
	 * @param activityTypeId ID
	 * @return
	 */
	@PostMapping("/crmActivity/addActivity")
	Result addActivity(@RequestParam("type") Integer type, @RequestParam("activityType") Integer activityType, @RequestParam("activityTypeId") Integer activityTypeId);

	@PostMapping(value = "/crmField/batchUpdateEsData")
	Result batchUpdateEsData(@RequestParam("id") String id, @RequestParam("name") String name);

	@PostMapping("/crmCustomerJob/updateReceivablesPlan")
	Result updateReceivablesPlan();


	@PostMapping("/crmCustomerJob/putInInternational")
	Result putInInternational();

	@PostMapping("/crmCustomerPool/queryPoolNameListByAuth")
	Result<List> queryPoolNameListByAuth();

	@PostMapping("/crmField/queryExamineField")
	public Result<List<ExamineField>> queryExamineField(@RequestParam("label") Integer label);

	@PostMapping("/crmCustomer/queryPageList")
	@ApiOperation("")
	Result<BasePage<Map<String, Object>>> queryCustomerPageList(@RequestBody CrmSearchBO search);

}
