package com.megazone.crm.controller;


import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.crm.common.log.CrmBusinessTypeLog;
import com.megazone.crm.entity.PO.CrmBusinessType;
import com.megazone.crm.service.ICrmBusinessTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crmBusinessType")
@Api(tags = "")
@SysLog(logClass = CrmBusinessTypeLog.class)
public class CrmBusinessTypeController {

	@Autowired
	private ICrmBusinessTypeService crmBusinessTypeService;

	@PostMapping("/queryBusinessTypeList")
	@ApiOperation("")
	public Result<BasePage<CrmBusinessType>> queryBusinessTypeList(@RequestBody PageEntity pageEntity) {
		BasePage<CrmBusinessType> crmBusinessTypeBasePage = crmBusinessTypeService.queryBusinessTypeList(pageEntity);
		return Result.ok(crmBusinessTypeBasePage);
	}

	@PostMapping("/getBusinessType")
	@ApiOperation("ID")
	public Result<CrmBusinessType> getBusinessType(@RequestParam("id") Integer id) {
		CrmBusinessType businessType = crmBusinessTypeService.getBusinessType(id);
		return Result.ok(businessType);
	}

	@PostMapping("/setBusinessType")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "#jsonObject[crmBusinessType].name", detail = "'Setup business: '+#jsonObject[crmBusinessType].name")
	public Result setBusinessType(@RequestBody JSONObject jsonObject) {
		crmBusinessTypeService.addBusinessType(jsonObject);
		return Result.ok();
	}


	@PostMapping("/deleteById")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.DELETE)
	public Result deleteById(@RequestParam("id") Integer typeId) {
		crmBusinessTypeService.deleteById(typeId);
		return Result.ok();
	}

	@PostMapping("/updateStatus")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE)
	public Result updateStatus(@RequestParam("typeId") Integer typeId, @RequestParam("status") Integer status) {
		crmBusinessTypeService.updateStatus(typeId, status);
		return Result.ok();
	}
}

