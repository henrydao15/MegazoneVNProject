package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.BO.QueryEmployFieldManageBO;
import com.megazone.hrm.entity.VO.EmployeeFieldManageVO;
import com.megazone.hrm.service.IHrmEmployeeFieldManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2021-04-14
 */
@RestController
@RequestMapping("/hrmEmployeeFieldManage")
@Api(tags = "-")
public class HrmEmployeeFieldManageController {
	@Autowired
	private IHrmEmployeeFieldManageService employeeFieldManageService;

	@PostMapping("/queryEmployeeManageField")
	@ApiOperation("")
	public Result<List<EmployeeFieldManageVO>> queryEmployeeManageField(QueryEmployFieldManageBO queryEmployFieldManageBO) {

		List<EmployeeFieldManageVO> manageFields = employeeFieldManageService.queryEmployeeManageField(queryEmployFieldManageBO);
		return Result.ok(manageFields);
	}

	@PostMapping("/setEmployeeManageField")
	@ApiOperation("")
	public Result setEmployeeManageField(@RequestBody List<EmployeeFieldManageVO> manageFields) {
		employeeFieldManageService.setEmployeeManageField(manageFields);
		return Result.ok();
	}

}

