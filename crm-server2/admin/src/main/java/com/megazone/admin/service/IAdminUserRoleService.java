package com.megazone.admin.service;

import com.megazone.admin.entity.PO.AdminUserRole;
import com.megazone.core.servlet.BaseService;

import java.util.List;

public interface IAdminUserRoleService extends BaseService<AdminUserRole> {

	void saveByUserId(Long userId, boolean isRemove, List<String> roleId);
}
