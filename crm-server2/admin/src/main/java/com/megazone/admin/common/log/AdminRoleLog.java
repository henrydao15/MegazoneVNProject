package com.megazone.admin.common.log;

import com.megazone.admin.entity.PO.AdminRole;
import com.megazone.admin.service.IAdminRoleService;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;

public class AdminRoleLog {
	private IAdminRoleService adminRoleService = ApplicationContextHolder.getBean(IAdminRoleService.class);

	public Content delete(Integer roleId) {
		AdminRole adminRole = adminRoleService.getById(roleId);
		return new Content(adminRole.getRoleName(), "Deleted role:" + adminRole.getRoleName(), BehaviorEnum.DELETE);
	}

	public Content copy(Integer roleId) {
		AdminRole adminRole = adminRoleService.getById(roleId);
		return new Content(adminRole.getRoleName(), "Copy the role:" + adminRole.getRoleName(), BehaviorEnum.COPY);
	}

	public Content deleteWorkRole(Integer roleId) {
		AdminRole adminRole = adminRoleService.getById(roleId);
		return new Content(adminRole.getRoleName(), "Deleted project role:" + adminRole.getRoleName(), BehaviorEnum.DELETE);
	}
}
