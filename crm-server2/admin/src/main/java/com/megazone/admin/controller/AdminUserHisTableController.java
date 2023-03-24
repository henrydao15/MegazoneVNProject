package com.megazone.admin.controller;

import com.megazone.admin.service.IAdminUserHisTableService;
import com.megazone.core.common.Result;
import com.megazone.core.feign.admin.entity.CallUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Authorized agent related interface")
@RequestMapping("/adminUserHisTable")
public class AdminUserHisTableController {

	@Autowired
	private IAdminUserHisTableService adminUserHisTableService;

	@PostMapping("/authorize")
	@ApiOperation("Employee Agent Authorization")
	public Result<Boolean> authorize(@RequestBody CallUser callUser) {
		return Result.ok(adminUserHisTableService.authorize(callUser.getUserIds(), callUser.getState(), callUser.getHisUse()));
	}

	@PostMapping("/checkAuth")
	@ApiOperation("Determine whether the user is an agent")
	public Result<Integer> checkAuth() {
		return Result.ok(adminUserHisTableService.checkAuth());
	}

}
