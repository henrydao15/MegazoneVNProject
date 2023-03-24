package com.megazone.admin.service.impl;

import com.megazone.admin.entity.PO.AdminRoleMenu;
import com.megazone.admin.mapper.AdminRoleMenuMapper;
import com.megazone.admin.service.IAdminRoleMenuService;
import com.megazone.core.common.Const;
import com.megazone.core.servlet.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-04-27
 */
@Service
public class AdminRoleMenuServiceImpl extends BaseServiceImpl<AdminRoleMenuMapper, AdminRoleMenu> implements IAdminRoleMenuService {

	@Override
	public void saveRoleMenu(Integer roleId, List<Integer> menuIdList) {
		List<AdminRoleMenu> adminRoleMenuList = new ArrayList<>();
		menuIdList.forEach(menuId -> {
			AdminRoleMenu adminRoleMenu = new AdminRoleMenu();
			adminRoleMenu.setMenuId(menuId);
			adminRoleMenu.setRoleId(roleId);
			adminRoleMenuList.add(adminRoleMenu);
		});
		saveBatch(adminRoleMenuList, Const.BATCH_SAVE_SIZE);
	}
}
