package com.megazone.core.feign.admin.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.entity.*;
import com.megazone.core.feign.admin.service.AdminService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class AdminServiceImpl implements AdminService {

	/**
	 * @param userId ID
	 * @return data
	 */
	@Override
	public Result<UserInfo> getUserInfo(Long userId) {
		return R.ok(null);
	}

	/**
	 * @param deptId ID
	 * @return
	 */
	@Override
	public Result<String> queryDeptName(Integer deptId) {
		return Result.ok("");
	}

	/**
	 * @param deptId ID
	 * @return data
	 */
	@Override
	public Result<List<Integer>> queryChildDeptId(Integer deptId) {
		return R.ok(new ArrayList<>());
	}

	/**
	 * @param userId ID 0
	 * @return data
	 */
	@Override
	public Result<List<Long>> queryChildUserId(Long userId) {
		return R.ok(new ArrayList<>());
	}

	/**
	 * @return data
	 */
	@Override
	public Result<List<Long>> queryUserList(Integer type) {
		return R.ok(Collections.emptyList());
	}

	/**
	 * @param name
	 * @return data
	 */
	@Override
	public Result<List<AdminConfig>> queryConfigByName(String name) {
		return R.ok(new ArrayList<>());
	}

	/**
	 * @param name
	 * @return data
	 */
	@Override
	public Result<AdminConfig> queryFirstConfigByName(String name) {
		return R.ok((AdminConfig) null);
	}

	@Override
	public Result<List<Long>> queryNormalUserByIds(Collection<Long> ids) {
		return R.ok(new ArrayList<>());
	}

	/**
	 * @param userId id
	 * @return data
	 */
	@Override
	public Result<SimpleUser> queryUserById(Long userId) {
		return R.ok(null);
	}

	/**
	 * @param ids id
	 * @return data
	 */
	@Override
	public Result<List<SimpleDept>> queryDeptByIds(Collection<Integer> ids) {
		return R.ok(new ArrayList<>());
	}

	/**
	 * @param ids id
	 * @return data
	 */
	@Override
	public Result<List<Long>> queryUserByDeptIds(Collection<Integer> ids) {
		return R.ok(Collections.emptyList());
	}

	@Override
	public Result<Integer> queryDataType(Long userId, Integer menuId) {
		return R.ok(1);
	}

	/**
	 * @param userId ID
	 * @param menuId ID
	 * @return
	 */
	@Override
	public Result<List<Long>> queryUserByAuth(Long userId, Integer menuId) {
		return R.ok(Collections.emptyList());
	}

	@Override
	public Result<Integer> queryWorkRole(Integer label) {
		return R.ok(1);
	}

	/**
	 * @param roleType
	 * @return type
	 */
	@Override
	public Result<List<Integer>> queryRoleByRoleType(Integer roleType) {
		return R.ok(Collections.emptyList());
	}

	@Override
	public Result<List<AdminRole>> queryRoleByRoleTypeAndUserId(Integer type) {
		return R.ok(Collections.emptyList());
	}

	@Override
	public Result updateAdminConfig(AdminConfig adminConfig) {
		return R.ok();
	}

	@Override
	public Result<JsonNode> auth() {
		return R.ok(new JSONObject().node);
	}

	@Override
	public Result saveOrUpdateMessage(AdminMessage message) {
		return R.error(SystemCodeEnum.SYSTEM_SERVER_ERROR);
	}

	@Override
	public Result<AdminMessage> getMessageById(Long messageId) {
		return R.ok((AdminMessage) null);
	}

	@Override
	public Result<AdminConfig> queryFirstConfigByNameAndValue(String name, String value) {
		return R.ok(new AdminConfig().setStatus(0));
	}

	@Override
	public Result<Integer> queryMenuId(String realm1, String realm2, String realm3) {
		return R.ok(0);
	}

	@Override
	public Result<List<Long>> queryUserIdByRealName(List<String> realNames) {
		return Result.ok(Collections.emptyList());
	}

	@Override
	public Result<UserInfo> queryLoginUserInfo(Long userId) {
		return Result.ok(new UserInfo());
	}

	@Override
	public Result<Long> queryUserIdByUserName(String userName) {
		return Result.ok(0L);
	}

	@Override
	public Result<List<UserInfo>> queryUserInfoList() {
		return Result.ok(Collections.emptyList());
	}

	@Override
	public Result<List<Long>> queryUserIdByRoleId(Integer roleId) {
		return Result.ok(Collections.emptyList());
	}

}
