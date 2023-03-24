package com.megazone.crm.controller;

import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.crm.entity.PO.CrmMarketingForm;
import com.megazone.crm.service.ICrmMarketingFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @date 2020/12/2
 */
@RestController
@RequestMapping("/crmMarketingForm")
@Api(tags = "")
public class CrmMarketingFormController {

	@Autowired
	private ICrmMarketingFormService crmMarketingFormService;


	@PostMapping("/saveOrUpdate")
	@ApiOperation(value = "")
	public Result<CrmMarketingForm> saveOrUpdateCrmMarketingForm(@Validated @RequestBody CrmMarketingForm crmMarketingForm) {
		return Result.ok(crmMarketingFormService.saveOrUpdateCrmMarketingForm(crmMarketingForm));
	}

	@PostMapping("/page")
	@ApiOperation(value = "")
	public Result<BasePage<CrmMarketingForm>> queryCrmMarketingFormList(@Validated @RequestBody PageEntity pageEntity) {
		return Result.ok(crmMarketingFormService.queryCrmMarketingFormList(pageEntity));
	}

	@PostMapping("/list")
	@ApiOperation(value = "")
	public Result<List<CrmMarketingForm>> queryAllCrmMarketingFormList() {
		return Result.ok(crmMarketingFormService.queryAllCrmMarketingFormList());
	}

	@PostMapping("/delete")
	@ApiOperation(value = "")
	public Result deleteCrmMarketingForm(@RequestParam("id") Integer id) {
		crmMarketingFormService.deleteCrmMarketingForm(id);
		return Result.ok();
	}

	@PostMapping("/updateStatus")
	@ApiOperation(value = "")
	public Result deleteCrmMarketingForm(@Validated @RequestBody CrmMarketingForm crmMarketingForm) {
		crmMarketingFormService.updateStatus(crmMarketingForm);
		return Result.ok();
	}

}
