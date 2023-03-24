package com.megazone.admin.common.log;

import com.megazone.admin.service.IAdminDeptService;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;

public class AdminDeptLog {
	private IAdminDeptService adminDeptService = ApplicationContextHolder.getBean(IAdminDeptService.class);

	public Content deleteDept(Integer deptId) {
		String deptName = adminDeptService.getNameByDeptId(deptId);
		return new Content(deptName, "Deleted department:" + deptName, BehaviorEnum.DELETE);
	}
}
