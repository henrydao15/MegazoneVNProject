package com.megazone.admin.common.log;

import com.megazone.admin.entity.BO.AdminUserStatusBO;
import com.megazone.admin.entity.PO.AdminUser;
import com.megazone.admin.service.IAdminUserService;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.utils.UserCacheUtil;

import java.util.ArrayList;
import java.util.List;

public class AdminUserLog {

	private IAdminUserService adminUserService = ApplicationContextHolder.getBean(IAdminUserService.class);

	public Content usernameEdit(Integer id, String username, String password) {
		AdminUser adminUser = adminUserService.getById(id);
		return new Content(adminUser.getRealname(), "Reset account password:" + adminUser.getRealname());
	}

	public List<Content> setUserStatus(AdminUserStatusBO adminUserStatusBO) {
		List<Content> contentList = new ArrayList<>();
		String detail;
		if (adminUserStatusBO.getStatus() == 1) {
			detail = "enabled";
		} else {
			detail = "disabled";
		}
		for (Long id : adminUserStatusBO.getIds()) {
			String userName = UserCacheUtil.getUserName(id);
			contentList.add(new Content(userName, detail + userName, BehaviorEnum.UPDATE));
		}
		return contentList;
	}

	public List<Content> resetPassword(AdminUserStatusBO adminUserStatusBO) {
		List<Content> contentList = new ArrayList<>();
		for (Long id : adminUserStatusBO.getIds()) {
			String userName = UserCacheUtil.getUserName(id);
			contentList.add(new Content(userName, "Reset password:" + userName, BehaviorEnum.UPDATE));
		}
		return contentList;
	}
}
