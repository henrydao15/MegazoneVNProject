package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.hrm.common.log.HrmSalaryChangeTemplateLog;
import com.megazone.hrm.entity.BO.SetChangeTemplateBO;
import com.megazone.hrm.entity.VO.ChangeSalaryOptionVO;
import com.megazone.hrm.entity.VO.QueryChangeTemplateListVO;
import com.megazone.hrm.service.IHrmSalaryChangeTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-05
 */
@RestController
@RequestMapping("/hrmSalaryChangeTemplate")
@Api(tags = "-")
@SysLog(subModel = SubModelType.HRM_SALARY_FILE, logClass = HrmSalaryChangeTemplateLog.class)
public class HrmSalaryChangeTemplateController {

	@Autowired
	private IHrmSalaryChangeTemplateService salaryChangeTemplateService;

	@PostMapping("/queryChangeSalaryOption")
	@ApiOperation("")
	public Result<List<ChangeSalaryOptionVO>> queryChangeSalaryOption() {
		List<ChangeSalaryOptionVO> list = salaryChangeTemplateService.queryChangeSalaryOption();
		return Result.ok(list);
	}

	@PostMapping("/setChangeTemplate")
	@ApiOperation("/")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#setChangeTemplateBO.templateName", detail = "'Added fixed salary adjustment template: '+#setChangeTemplateBO.templateName")
	public Result setChangeTemplate(@RequestBody SetChangeTemplateBO setChangeTemplateBO) {
		salaryChangeTemplateService.setChangeTemplate(setChangeTemplateBO);
		return Result.ok();
	}

	@PostMapping("/queryChangeTemplateList")
	@ApiOperation("")
	public Result<List<QueryChangeTemplateListVO>> queryChangeTemplateList() {
		List<QueryChangeTemplateListVO> list = salaryChangeTemplateService.queryChangeTemplateList();
		return Result.ok(list);
	}

	@PostMapping("/deleteChangeTemplate/{id}")
	@ApiOperation("")
	public Result deleteChangeTemplate(@PathVariable Integer id) {
		salaryChangeTemplateService.deleteChangeTemplate(id);
		return Result.ok();
	}
}

