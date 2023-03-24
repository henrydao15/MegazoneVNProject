package com.megazone.admin.service;

import com.megazone.admin.common.AdminRoleTypeEnum;
import com.megazone.admin.entity.PO.AdminMenu;
import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseService;

import java.util.List;
import java.util.Map;

public interface IAdminMenuService extends BaseService<AdminMenu> {
	List<AdminMenu> queryMenuList(Long userId);

	Map<String, Long> queryPoolReadAuth(Long userId, Integer deptId);

	JSONObject getMenuListByType(AdminRoleTypeEnum typeEnum);

	Integer queryMenuId(String realm1, String realm2, String realm3);
}
