package com.megazone.admin.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.admin.common.AdminRoleTypeEnum;
import com.megazone.admin.common.log.AdminRoleLog;
import com.megazone.admin.entity.BO.AdminRoleBO;
import com.megazone.admin.entity.PO.AdminModelSort;
import com.megazone.admin.entity.PO.AdminRole;
import com.megazone.admin.entity.VO.AdminRoleVO;
import com.megazone.admin.service.IAdminModelSortService;
import com.megazone.admin.service.IAdminRoleAuthService;
import com.megazone.admin.service.IAdminRoleService;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adminRole")
@Api(tags = "Character Module")
@SysLog(subModel = SubModelType.ADMIN_ROLE_PERMISSIONS, logClass = AdminRoleLog.class)
public class AdminRoleController {
	@Autowired
	private IAdminRoleService adminRoleService;

	@Autowired
	private IAdminModelSortService adminModelSortService;

	@Autowired
	private IAdminRoleAuthService adminRoleAuthService;

	@PostMapping("/auth")
	@ApiOperation("role permissions")
	public Result<JsonNode> auth() {
		JSONObject object = adminRoleService.auth(UserUtil.getUserId());
		AdminModelSort one = adminModelSortService.lambdaQuery().select(AdminModelSort::getModel).eq(AdminModelSort::getType, 1).eq(AdminModelSort::getUserId, UserUtil.getUserId()).orderByAsc(AdminModelSort::getSort).last("limit 1").one();
		object.fluentPut("firstModel", one != null ? one.getModel() : "");
		return R.ok(object.node);
	}

	@PostMapping("/queryNoAuthMenu")
	@ApiOperation("Get unauthorized menu")
	public Result<List<String>> queryNoAuthMenu(@RequestParam("userId") @NotNull Long userId) {
		return R.ok(adminRoleService.queryNoAuthMenu(userId));
	}

	@PostMapping("/getRoleList")
	@ApiOperation("Query the queryable roles when adding new employees")
	public Result<List<AdminRoleVO>> getRoleList() {
		return R.ok(adminRoleService.getRoleList());
	}

	@PostMapping("/getAllRoleList")
	@ApiOperation("Global role query")
	public Result<List<AdminRoleVO>> getAllRoleList() {
		List<AdminRoleVO> allRoleList = adminRoleService.getAllRoleList();
		return R.ok(allRoleList);
	}

	@PostMapping("/getRoleTypeList")
	@ApiOperation("Get a list of role types")
	public Result<List<Map<String, Object>>> getRoleTypeList() {
		List<Map<String, Object>> data = new ArrayList<>(6);
		data.add(new JSONObject().fluentPut("name", "System Management Role").fluentPut("roleType", 1).getInnerMapObject());
		data.add(new JSONObject().fluentPut("name", "Office Management Role").fluentPut("roleType", 7).getInnerMapObject());
		data.add(new JSONObject().fluentPut("name", "Customer Management Role").fluentPut("roleType", 2).getInnerMapObject());
		data.add(new JSONObject().fluentPut("name", "Project Management Role").fluentPut("roleType", 8).getInnerMapObject());
		return R.ok(data);
	}

	@PostMapping("/getRoleByType/{type}")
	@ApiOperation("Query role by role type")
	public Result<List<AdminRole>> getRoleByType(@PathVariable("type") Integer type) {
		AdminRoleTypeEnum roleTypeEnum = AdminRoleTypeEnum.parse(type);
		List<AdminRole> roleByType = adminRoleService.getRoleByType(roleTypeEnum);
		return R.ok(roleByType);
	}

	@PostMapping("/queryRoleByRoleType")
	@ApiExplain("Query roles by role type")
	public Result<List<Integer>> queryRoleByRoleType(@RequestParam("type") Integer type) {
		List<AdminRole> recordList = adminRoleService.lambdaQuery().select(AdminRole::getRoleId).eq(AdminRole::getRoleType, type).list();
		return R.ok(recordList.stream().map(AdminRole::getRoleId).collect(Collectors.toList()));
	}

	@PostMapping("/queryRoleByRoleTypeAndUserId")
	@ApiExplain("Query the current user's role under a module")
	public Result<List<AdminRole>> queryRoleByRoleTypeAndUserId(@RequestParam("type") Integer type) {
		List<AdminRole> recordList = adminRoleService.queryRoleByRoleTypeAndUserId(type);
		return R.ok(recordList);
	}


	@PostMapping("/queryRoleListByUserId")
	@ApiExplain("Query roles by user id")
	public Result<List<AdminRole>> queryRoleListByUserId(@RequestBody List<Long> userIds) {
		return R.ok(adminRoleService.queryRoleListByUserId(userIds));
	}

	@PostMapping("/queryDataType")
	@ApiExplain("Query data permission")
	public Result<Integer> queryDataType(@RequestParam("userId") Long userId, @RequestParam("menuId") Integer menuId) {
		Integer dataType = adminRoleService.queryDataType(userId, menuId);
		return R.ok(dataType);
	}

	@PostMapping("/queryUserByAuth")
	@ApiExplain("Query data permission")
	public Result<Collection<Long>> queryUserByAuth(@RequestParam("userId") Long userId, @RequestParam("menuId") Integer menuId) {
		Collection<Long> longs = adminRoleService.queryUserByAuth(userId, menuId);
		return R.ok(longs);
	}

	@PostMapping("/add")
	@ApiOperation("Add role")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#adminRole.roleName", detail = "'Added role:'+#adminRole.roleName")
	public Result add(@RequestBody AdminRole adminRole) {
		adminRoleService.add(adminRole);
		return R.ok();
	}

	@PostMapping("/update")
	@ApiOperation("Modify role")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#adminRole.roleName", detail = "'Modified role:'+#adminRole.roleName")
	public Result update(@RequestBody AdminRole adminRole) {
		adminRoleService.add(adminRole);
		return R.ok();
	}

	@PostMapping("/delete")
	@ApiOperation("Delete role")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result delete(@RequestParam("roleId") Integer roleId) {
		adminRoleService.delete(roleId);
		return R.ok();
	}

	@PostMapping("/copy")
	@ApiOperation("Copy Role")
	@SysLogHandler(behavior = BehaviorEnum.COPY)
	public Result copy(@RequestParam("roleId") Integer roleId) {
		adminRoleService.copy(roleId);
		return R.ok();
	}

	@PostMapping("/relatedDeptUser")
	@ApiOperation("The role is associated with the employee department")
	public Result relatedDeptUser(@RequestBody AdminRoleBO adminRoleBO) {
		adminRoleService.relatedDeptUser(adminRoleBO.getUserIds(), adminRoleBO.getDeptIds(), adminRoleBO.getRoleIds());
		return R.ok();
	}

	@PostMapping("/relatedUser")
	@ApiOperation("Role Associated Employee")
	public Result relatedUser(@RequestBody AdminRoleBO adminRoleBO) {
		adminRoleService.relatedUser(adminRoleBO.getUserIds(), adminRoleBO.getRoleIds());
		return R.ok();
	}

	@PostMapping("/unbindingUser")
	@ApiOperation("Cancel the role associated employee")
	public Result unbindingUser(@RequestParam("userId") Long userId, @RequestParam("roleId") Integer roleId) {
		adminRoleService.unbindingUser(userId, roleId);
		return R.ok();
	}

	@PostMapping("/updateRoleMenu")
	@ApiOperation("Save role menu relationship")
	public Result updateRoleMenu(@RequestBody AdminRole adminRole) {
		adminRoleService.updateRoleMenu(adminRole);
		return R.ok();
	}

	@PostMapping("/updateAuthRole/{roleId}")
	@ApiOperation("Save the relationship between the role and the visible role")
	public Result updateAuthRole(@PathVariable("roleId") Integer roleId, @RequestBody List<Integer> authRoleIds) {
		adminRoleAuthService.saveRoleAuth(roleId, authRoleIds);
		return Result.ok();
	}

	@PostMapping("/queryAuthRole/{roleId}")
	@ApiOperation("Query the relationship between the role and the role that can be seen")
	public Result<List<Integer>> queryAuthRole(@PathVariable("roleId") Integer roleId) {
		List<Integer> roleIdList = adminRoleAuthService.queryByRoleId(roleId);
		return Result.ok(roleIdList);
	}

	@PostMapping(value = "/queryWorkRole")
	@ApiExplain("Query project management roles")
	public Result<Integer> queryWorkRole(@RequestParam("label") Integer label) {
		Integer role = adminRoleService.queryWorkRole(label);
		return R.ok(role);
	}

	@PostMapping(value = "/setWorkRole")
	@ApiExplain("Set project management role")
	@SysLogHandler(subModel = SubModelType.WORK_PROJECT, behavior = BehaviorEnum.SAVE, object = "#object[roleName]", detail = "'Set project role:'+#object[roleName]")
	public Result setWorkRole(@RequestBody JSONObject object) {
		adminRoleService.setWorkRole(object);
		return R.ok();
	}

	@PostMapping(value = "/deleteWorkRole")
	@ApiExplain("Delete project management role")
	@SysLogHandler(subModel = SubModelType.WORK_PROJECT, behavior = BehaviorEnum.DELETE)
	public Result deleteWorkRole(@RequestParam("roleId") Integer roleId) {
		adminRoleService.deleteWorkRole(roleId);
		return R.ok();
	}

	@PostMapping(value = "/queryProjectRoleList")
	@ApiOperation("Query the list of project management roles")
	public Result<List<AdminRole>> queryProjectRoleList() {
		List<AdminRole> adminRoles = adminRoleService.queryProjectRoleList();
		return R.ok(adminRoles);
	}

	@PostMapping(value = "/queryWorkRoleList")
	@ApiOperation("Query the list of project management roles")
	public Result<List<AdminRole>> queryWorkRoleList() {
		List<AdminRole> adminRoles = adminRoleService.queryRoleList();
		return R.ok(adminRoles);
	}

	@PostMapping("/adminRole/queryUserIdByRoleId")
	@ApiExplain("Query user list based on role ID")
	public Result<List<Long>> queryUserIdByRoleId(@RequestParam("roleId") Integer roleId) {
		List<Long> userIds = adminRoleService.queryUserIdByRoleId(roleId);
		return R.ok(userIds);
	}
}

