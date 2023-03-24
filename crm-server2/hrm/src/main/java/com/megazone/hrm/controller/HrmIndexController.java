package com.megazone.hrm.controller;

import com.megazone.core.common.Result;
import com.megazone.hrm.entity.PO.HrmEmployee;
import com.megazone.hrm.service.IHrmEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Api(tags = "index")
@Slf4j
public class HrmIndexController {

	@Autowired
	private IHrmEmployeeService employeeService;


	@PostMapping("/hrm/queryEmployeeListByIds")
	@ApiOperation("ids")
	public Result<Set<HrmEmployee>> queryEmployeeListByIds(@RequestBody List<Integer> employeeIds) {
		List<HrmEmployee> employees = employeeService.query().select("employee_id", "employee_name", "mobile", "email", "sex", "post")
				.eq("is_del", 0)
				.in("employee_id", employeeIds).list();
		Set<HrmEmployee> list = new HashSet<>(employees);
		return Result.ok(list);
	}


}
