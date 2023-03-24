package com.megazone.admin.service;

import com.megazone.admin.entity.PO.AdminRoleMenu;
import com.megazone.core.servlet.BaseService;

import java.util.List;

public interface IAdminRoleMenuService extends BaseService<AdminRoleMenu> {
	void saveRoleMenu(Integer roleId, List<Integer> menuIdList);
}
