package com.megazone.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.megazone.admin.entity.PO.AdminUserRole;
import com.megazone.admin.mapper.AdminUserRoleMapper;
import com.megazone.admin.service.IAdminRoleAuthService;
import com.megazone.admin.service.IAdminUserRoleService;
import com.megazone.core.common.Const;
import com.megazone.core.servlet.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-04-27
 */
@Service
public class AdminUserRoleServiceImpl extends BaseServiceImpl<AdminUserRoleMapper, AdminUserRole> implements IAdminUserRoleService {

	@Autowired
	private IAdminRoleAuthService adminRoleAuthService;

	/**
	 * @param userId   ID
	 * @param isRemove
	 * @param roleIds
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveByUserId(Long userId, boolean isRemove, List<String> roleIds) {
		if (isRemove) {
			QueryWrapper<AdminUserRole> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("user_id", userId);
			remove(queryWrapper);
		}
		List<AdminUserRole> adminUserRoleList = new ArrayList<>();
		for (String roleId : roleIds) {
			Integer id = Integer.valueOf(roleId);
			adminUserRoleList.add(new AdminUserRole().setUserId(userId).setRoleId(id));
		}
		boolean queryAllRole = adminRoleAuthService.isQueryAllRole();
		if (!queryAllRole) {
			Set<Integer> authByUser = adminRoleAuthService.queryAuthByUser();
			adminUserRoleList.removeIf(userRole -> !authByUser.contains(userRole.getRoleId()));
		}
		saveBatch(adminUserRoleList, Const.BATCH_SAVE_SIZE);
	}
}
