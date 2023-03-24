package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.hrm.common.log.HrmSalarySlipTemplateLog;
import com.megazone.hrm.entity.BO.AddSlipTemplateBO;
import com.megazone.hrm.entity.PO.HrmSalarySlipTemplate;
import com.megazone.hrm.service.IHrmSalarySlipTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/hrmSalarySlipTemplate")
@Api(tags = "-")
@SysLog(subModel = SubModelType.HRM_SALARY_SLIP, logClass = HrmSalarySlipTemplateLog.class)
public class HrmSalarySlipTemplateController {

	@Autowired
	private IHrmSalarySlipTemplateService salarySlipTemplateService;

	@PostMapping("/querySlipTemplateList")
	@ApiOperation("")
	public Result<List<HrmSalarySlipTemplate>> querySlipTemplateList() {
		List<HrmSalarySlipTemplate> slipTemplates = salarySlipTemplateService.list();
		return Result.ok(slipTemplates);
	}

	@PostMapping("/addSlipTemplate")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#addSlipTemplateBO.templateName", detail = "'Added payslip template: '+#addSlipTemplateBO.templateName")
	public Result addSlipTemplate(@Validated @RequestBody AddSlipTemplateBO addSlipTemplateBO) {
		salarySlipTemplateService.addSlipTemplate(addSlipTemplateBO);
		return Result.ok();
	}

	@PostMapping("/deleteSlipTemplate/{templateId}")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteSlipTemplate(@PathVariable Integer templateId) {
		salarySlipTemplateService.deleteSlipTemplate(templateId);
		return Result.ok();
	}

}

