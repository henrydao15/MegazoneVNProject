package com.megazone.admin.controller;


import com.megazone.admin.common.log.AdminDeptLog;
import com.megazone.admin.entity.BO.AdminDeptBO;
import com.megazone.admin.entity.BO.AdminDeptQueryBO;
import com.megazone.admin.entity.VO.AdminDeptVO;
import com.megazone.admin.service.IAdminDeptService;
import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.feign.admin.entity.SimpleDept;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/adminDept")
@Api(tags = "Department management related interface")
@SysLog(subModel = SubModelType.ADMIN_DEPARTMENT_MANAGEMENT, logClass = AdminDeptLog.class)
public class AdminDeptController {

	@Autowired
	private IAdminDeptService adminDeptService;

	@PostMapping("/queryDeptTree")
	@ApiOperation("Query department list tree")
	public Result<List<AdminDeptVO>> queryDeptTree(@RequestBody AdminDeptQueryBO queryBO) {
		List<AdminDeptVO> deptList = adminDeptService.queryDeptTree(queryBO);
		return Result.ok(deptList);
	}

	@PostMapping("/addDept")
	@ApiOperation("Add department")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#adminDept.name", detail = "'Added department:'+#adminDept.name")
	public Result addDept(@RequestBody @Valid AdminDeptBO adminDept) {
		adminDeptService.addDept(adminDept);
		return Result.ok();
	}

	@PostMapping("/setDept")
	@ApiOperation("Modify Department")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#adminDept.name", detail = "'Modified department:'+#adminDept.name")
	public Result setDept(@RequestBody @Valid AdminDeptBO adminDept) {
		adminDeptService.setDept(adminDept);
		return Result.ok();
	}

	@PostMapping("/deleteDept/{deptId}")
	@ApiOperation("Delete department")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteDept(@PathVariable("deptId") Integer deptId) {
		adminDeptService.deleteDept(deptId);
		return Result.ok();
	}

	@RequestMapping("/getNameByDeptId")
	@ApiExplain("Get the department name according to the department ID")
	public Result getNameByDeptId(Integer deptId) {
		return R.ok(adminDeptService.getNameByDeptId(deptId));
	}

	@RequestMapping("/queryChildDeptId")
	@ApiExplain("Sub-departments under the department ID")
	public Result<List<Integer>> queryChildDeptId(@NotNull Integer deptId) {
		return R.ok(adminDeptService.queryChildDept(deptId));
	}

	@PostMapping("/queryDeptByIds")
	@ApiExplain("Get users by department ID")
	public Result<List<SimpleDept>> queryDeptByIds(@RequestBody List<Integer> ids) {
		List<SimpleDept> simpleDepts = adminDeptService.queryDeptByIds(ids);
		return R.ok(simpleDepts);
	}
}
