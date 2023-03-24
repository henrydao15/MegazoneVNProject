package com.megazone.admin.service;

import com.megazone.admin.entity.PO.AdminRoleAuth;
import com.megazone.core.servlet.BaseService;

import java.util.List;
import java.util.Set;

public interface IAdminRoleAuthService extends BaseService<AdminRoleAuth> {
	void saveRoleAuth(Integer roleId, List<Integer> authRoleIds);

	List<Integer> queryByRoleId(Integer roleId);

	Set<Integer> queryAuthByUser();

	boolean isQueryAllRole();
}
