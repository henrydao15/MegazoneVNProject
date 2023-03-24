package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.hrm.common.log.HrmDeptLog;
import com.megazone.hrm.entity.BO.AddDeptBO;
import com.megazone.hrm.entity.BO.QueryDeptListBO;
import com.megazone.hrm.entity.BO.QueryEmployeeByDeptIdBO;
import com.megazone.hrm.entity.VO.DeptVO;
import com.megazone.hrm.entity.VO.QueryEmployeeListByDeptIdVO;
import com.megazone.hrm.service.IHrmDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmDept")
@Api(tags = "-")
@SysLog(subModel = SubModelType.HRM_DEPT, logClass = HrmDeptLog.class)
public class HrmDeptController {

	@Autowired
	private IHrmDeptService deptService;

	@PostMapping("/addDept")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#addDeptBO.name", detail = "'Added department: '+#addDeptBO.name")
	public Result addDept(@Valid @RequestBody AddDeptBO addDeptBO) {
		deptService.addOrUpdate(addDeptBO);
		return Result.ok();
	}

	@PostMapping("/setDept")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result setDept(@Valid @RequestBody AddDeptBO addDeptBO) {
		deptService.addOrUpdate(addDeptBO);
		return Result.ok();
	}

	@PostMapping("/queryById/{deptId}")
	@ApiOperation("")
	public Result<DeptVO> queryById(@PathVariable("deptId") Integer deptId) {
		DeptVO deptVO = deptService.queryById(deptId);
		return Result.ok(deptVO);
	}

	@PostMapping("/queryTreeList")
	@ApiOperation("")
	public Result<List<DeptVO>> queryTreeList(@RequestBody QueryDeptListBO queryDeptListBO) {
		List<DeptVO> treeNode = deptService.queryTreeList(queryDeptListBO);
		return Result.ok(treeNode);
	}

	@PostMapping("/queryEmployeeByDeptId")
	@ApiOperation("id")
	public Result<BasePage<QueryEmployeeListByDeptIdVO>> queryEmployeeByDeptId(@RequestBody QueryEmployeeByDeptIdBO employeeByDeptIdBO) {
		BasePage<QueryEmployeeListByDeptIdVO> page = deptService.queryEmployeeByDeptId(employeeByDeptIdBO);
		return Result.ok(page);
	}

	@PostMapping("/deleteDeptById/{deptId}")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteDeptById(@PathVariable String deptId) {
		deptService.deleteDeptById(deptId);
		return Result.ok();
	}


}

