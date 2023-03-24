package com.megazone.core.feign.admin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.Result;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.entity.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author
 */
@Component
@FeignClient(name = "admin", contextId = "admin")
public interface AdminService {

	/**
	 * @param userId ID
	 * @return data
	 */
	@RequestMapping(value = "/adminUser/queryInfoByUserId")
	public Result<UserInfo> getUserInfo(@RequestParam("userId") Long userId);

	/**
	 * @param deptId ID
	 * @return
	 */
	@RequestMapping(value = "/adminDept/getNameByDeptId")
	public Result<String> queryDeptName(@RequestParam("deptId") Integer deptId);

	/**
	 * @param deptId ID
	 * @return data
	 */
	@RequestMapping(value = "/adminDept/queryChildDeptId")
	public Result<List<Integer>> queryChildDeptId(@RequestParam("deptId") Integer deptId);

	/**
	 * @param userId ID 0
	 * @return data
	 */
	@RequestMapping(value = "/adminUser/queryChildUserId")
	public Result<List<Long>> queryChildUserId(@RequestParam("userId") Long userId);

	/**
	 * @param type 1，2
	 * @return data
	 */
	@PostMapping(value = "/adminUser/queryAllUserList")
	public Result<List<Long>> queryUserList(@RequestParam(value = "type", required = false) Integer type);

	/**
	 * @param name
	 * @return data
	 */
	@PostMapping(value = "/adminConfig/queryConfigByName")
	public Result<List<AdminConfig>> queryConfigByName(@RequestParam("name") String name);

	/**
	 * @param name
	 * @return data
	 */
	@RequestMapping(value = "/adminConfig/queryFirstConfigByName")
	public Result<AdminConfig> queryFirstConfigByName(@RequestParam("name") String name);

	/**
	 * @param ids id
	 * @return data
	 */
	@PostMapping(value = "/adminUser/queryNormalUserByIds")
	public Result<List<Long>> queryNormalUserByIds(@RequestBody Collection<Long> ids);

	/**
	 * @param userId id
	 * @return data
	 */
	@PostMapping(value = "/adminUser/queryUserById")
	public Result<SimpleUser> queryUserById(@RequestParam("userId") Long userId);

	/**
	 * @param ids id
	 * @return data
	 */
	@PostMapping(value = "/adminDept/queryDeptByIds")
	public Result<List<SimpleDept>> queryDeptByIds(@RequestBody Collection<Integer> ids);

	/**
	 * @param ids id
	 * @return data
	 */
	@PostMapping(value = "/adminUser/queryUserByDeptIds")
	public Result<List<Long>> queryUserByDeptIds(@RequestBody Collection<Integer> ids);

	/**
	 * @param userId ID
	 * @param menuId ID
	 * @return
	 */
	@PostMapping(value = "/adminRole/queryDataType")
	public Result<Integer> queryDataType(@RequestParam("userId") Long userId, @RequestParam("menuId") Integer menuId);

	/**
	 * @param userId ID
	 * @param menuId Id
	 * @return
	 */
	@PostMapping(value = "/adminRole/queryUserByAuth")
	public Result<List<Long>> queryUserByAuth(@RequestParam("userId") Long userId, @RequestParam("menuId") Integer menuId);

	@PostMapping(value = "/adminRole/queryWorkRole")
	public Result<Integer> queryWorkRole(@RequestParam("label") Integer label);

	/**
	 * @param type
	 * @return type
	 */
	@PostMapping(value = "/adminRole/queryRoleByRoleType")
	public Result<List<Integer>> queryRoleByRoleType(@RequestParam("type") Integer type);

	/**
	 * @param type
	 * @return
	 */
	@PostMapping("/adminRole/queryRoleByRoleTypeAndUserId")
	public Result<List<AdminRole>> queryRoleByRoleTypeAndUserId(@RequestParam("type") Integer type);

	/**
	 * @param adminConfig
	 * @return data
	 */
	@PostMapping(value = "/adminConfig/updateAdminConfig")
	public Result updateAdminConfig(@RequestBody AdminConfig adminConfig);

	@PostMapping("/adminRole/auth")
	@ApiOperation("")
	Result<JsonNode> auth();

	@PostMapping("/adminMessage/saveOrUpdateMessage")
	public Result<Long> saveOrUpdateMessage(@RequestBody AdminMessage message);

	@PostMapping("/adminMessage/getById/{messageId}")
	public Result<AdminMessage> getMessageById(@PathVariable Long messageId);

	@ApiExplain(value = "config")
	@RequestMapping("/adminConfig/queryFirstConfigByNameAndValue")
	public Result<AdminConfig> queryFirstConfigByNameAndValue(@RequestParam("name") String name,
															  @RequestParam("value") String value);

	@RequestMapping("/adminMenu/queryMenuId")
	public Result<Integer> queryMenuId(@RequestParam("realm1") String realm1, @RequestParam("realm2") String realm2,
									   @RequestParam("realm3") String realm3);

	@PostMapping("/adminUser/queryUserIdByRealName")
	@ApiOperation("id")
	public Result<List<Long>> queryUserIdByRealName(@RequestParam("realNames") List<String> realNames);

	@PostMapping("/adminUser/queryLoginUserInfo")
	@ApiExplain("")
	public Result<UserInfo> queryLoginUserInfo(@RequestParam("userId") Long userId);

	@PostMapping("/adminUser/queryUserIdByUserName")
	@ApiExplain("id")
	Result<Long> queryUserIdByUserName(@RequestParam("userName") String userName);

	@PostMapping("/adminUser/queryAllUserInfoList")
	@ApiExplain("")
	public Result<List<UserInfo>> queryUserInfoList();

	@PostMapping("/adminRole/queryUserIdByRoleId")
	public Result<List<Long>> queryUserIdByRoleId(@RequestParam("roleId") Integer roleId);

}
