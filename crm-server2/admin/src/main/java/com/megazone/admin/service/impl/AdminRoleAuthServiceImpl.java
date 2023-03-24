package com.megazone.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.admin.entity.PO.AdminRoleAuth;
import com.megazone.admin.entity.PO.AdminRoleMenu;
import com.megazone.admin.mapper.AdminRoleAuthMapper;
import com.megazone.admin.service.IAdminRoleAuthService;
import com.megazone.admin.service.IAdminRoleMenuService;
import com.megazone.core.common.Const;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2021-04-23
 */
@Service
public class AdminRoleAuthServiceImpl extends BaseServiceImpl<AdminRoleAuthMapper, AdminRoleAuth> implements IAdminRoleAuthService {

	/**
	 * @param roleId      ID
	 * @param authRoleIds ID
	 */
	@Override
	public void saveRoleAuth(Integer roleId, List<Integer> authRoleIds) {
		removeByMap(Collections.singletonMap("role_id", roleId));
		List<AdminRoleAuth> authList = authRoleIds.stream().map(id -> {
			AdminRoleAuth adminRoleAuth = new AdminRoleAuth();
			adminRoleAuth.setRoleId(roleId);
			adminRoleAuth.setMenuId(935);
			adminRoleAuth.setAuthRoleId(id);
			return adminRoleAuth;
		}).collect(Collectors.toList());
		saveBatch(authList, Const.BATCH_SAVE_SIZE);
	}

	/**
	 * @param roleId roleId
	 * @return data
	 */
	@Override
	public List<Integer> queryByRoleId(Integer roleId) {
		LambdaQueryWrapper<AdminRoleAuth> wrapper = new LambdaQueryWrapper<>();
		wrapper.select(AdminRoleAuth::getAuthRoleId);
		wrapper.eq(AdminRoleAuth::getRoleId, roleId);
		return listObjs(wrapper, obj -> Integer.parseInt(obj.toString()));
	}

	/**
	 * @return roleIds
	 */
	@Override
	public Set<Integer> queryAuthByUser() {
		List<Integer> roles = UserUtil.getUser().getRoles();
		LambdaQueryWrapper<AdminRoleAuth> wrapper = new LambdaQueryWrapper<>();
		wrapper.select(AdminRoleAuth::getAuthRoleId);
		wrapper.in(AdminRoleAuth::getRoleId, roles);
		return new HashSet<>(listObjs(wrapper, obj -> Integer.parseInt(obj.toString())));
	}

	/**
	 * @return true
	 */
	@Override
	public boolean isQueryAllRole() {
		IAdminRoleMenuService roleMenuService = ApplicationContextHolder.getBean(IAdminRoleMenuService.class);
		LambdaQueryWrapper<AdminRoleMenu> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(AdminRoleMenu::getMenuId, 175).in(AdminRoleMenu::getRoleId, UserUtil.getUser().getRoles());
		return UserUtil.isAdmin() || roleMenuService.count(wrapper) > 0;
	}
}
