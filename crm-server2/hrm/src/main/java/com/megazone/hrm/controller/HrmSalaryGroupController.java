package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.hrm.common.log.HrmSalaryGroupLog;
import com.megazone.hrm.entity.BO.SetSalaryGroupBO;
import com.megazone.hrm.entity.VO.SalaryGroupPageListVO;
import com.megazone.hrm.service.IHrmSalaryGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/hrmSalaryGroup")
@Api(tags = "-")
@SysLog(logClass = HrmSalaryGroupLog.class)
public class HrmSalaryGroupController {

	@Autowired
	private IHrmSalaryGroupService salaryGroupService;


	@PostMapping("/addSalaryGroup")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT, behavior = BehaviorEnum.SAVE, object = "#salaryGroup.groupName", detail = "'Added new payroll group: '+#salaryGroup.groupName")
	public Result addSalaryGroup(@RequestBody SetSalaryGroupBO salaryGroup) {
		salaryGroupService.setSalaryGroup(salaryGroup);
		return Result.ok();
	}


	@PostMapping("/setSalaryGroup")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT, behavior = BehaviorEnum.SAVE, object = "#salaryGroup.groupName", detail = "'Modified payroll group: '+#salaryGroup.groupName")
	public Result setSalaryGroup(@RequestBody SetSalaryGroupBO salaryGroup) {
		salaryGroupService.setSalaryGroup(salaryGroup);
		return Result.ok();
	}


	@PostMapping("/querySalaryGroupPageList")
	@ApiOperation("")
	public Result<BasePage<SalaryGroupPageListVO>> querySalaryGroupPageList(@RequestBody PageEntity pageEntity) {
		BasePage<SalaryGroupPageListVO> page = salaryGroupService.querySalaryGroupPageList(pageEntity);
		return Result.ok(page);
	}


	@PostMapping("/delete/{groupId}")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT, behavior = BehaviorEnum.DELETE)
	public Result deleteSalaryGroup(@PathVariable Integer groupId) {
		salaryGroupService.removeById(groupId);
		return Result.ok();
	}


}

