package com.megazone.authorization.service.impl;

import com.megazone.authorization.entity.AdminUserStatusBO;
import com.megazone.authorization.service.AdminUserService;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminUserServiceImpl implements AdminUserService {


	/**
	 * Query user by username
	 *
	 * @param username username
	 * @return result information
	 */
	@Override
	public Result findByUsername(String username) {
		return Result.error(SystemCodeEnum.SYSTEM_NO_FOUND);
	}

	/**
	 * Query the role of the user by the user ID
	 *
	 * @param userId User ID
	 * @return data
	 */
	@Override
	public Result<List<Integer>> queryUserRoleIds(Long userId) {
		return Result.ok(new ArrayList<>());
	}

	@Override
	public Result<List<String>> queryNoAuthMenu(Long userId) {
		return Result.error(SystemCodeEnum.SYSTEM_NO_FOUND);
	}

	@Override
	public Result setUserStatus(AdminUserStatusBO adminUserStatusBO) {
		return Result.ok();
	}

	@Override
	public Result activateUser(AdminUserStatusBO adminUserStatusBO) {
		return Result.ok();
	}
}
