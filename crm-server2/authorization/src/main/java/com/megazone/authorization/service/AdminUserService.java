package com.megazone.authorization.service;

import com.megazone.authorization.entity.AdminUserStatusBO;
import com.megazone.core.common.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "admin")
public interface AdminUserService {

	/**
	 * Query user by username
	 *
	 * @param username username
	 * @return result information
	 */
	@RequestMapping(value = "/adminUser/findByUsername")
	Result findByUsername(@RequestParam("username") String username);

	/**
	 * Query the role of the user by the user ID
	 *
	 * @param userId User ID
	 * @return data
	 */
	@RequestMapping(value = "/adminUser/queryUserRoleIds")
	Result<List<Integer>> queryUserRoleIds(@RequestParam("userId") Long userId);

	@PostMapping("/adminUser/setUserStatus")
	@ApiOperation("disable enable")
	Result setUserStatus(@RequestBody AdminUserStatusBO adminUserStatusBO);

	@PostMapping("/adminUser/activateUser")
	@ApiOperation("Activate account")
	Result activateUser(@RequestBody AdminUserStatusBO adminUserStatusBO);


	/**
	 * Query menus without permission by user ID
	 *
	 * @param userId
	 * @return result information
	 * @date 2020/8/20 15:25
	 **/
	@PostMapping(value = "/adminRole/queryNoAuthMenu")
	Result<List<String>> queryNoAuthMenu(@RequestParam("userId") Long userId);
}
