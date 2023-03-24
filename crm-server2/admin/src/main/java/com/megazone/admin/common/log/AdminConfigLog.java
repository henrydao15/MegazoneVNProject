package com.megazone.admin.common.log;

import com.megazone.admin.entity.BO.ModuleSettingBO;
import com.megazone.admin.entity.PO.AdminConfig;
import com.megazone.admin.service.IAdminConfigService;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;

public class AdminConfigLog {

	private IAdminConfigService adminConfigService = ApplicationContextHolder.getBean(IAdminConfigService.class);

	public Content setModuleSetting(ModuleSettingBO moduleSetting) {
		AdminConfig adminConfig = adminConfigService.getById(moduleSetting.getSettingId());
		String detail;
		if (moduleSetting.getStatus() == 0) {
			detail = "Disabled";
		} else {
			detail = "enabled";
		}
		detail += adminConfig.getName();
		return new Content(adminConfig.getName(), detail, BehaviorEnum.UPDATE);
	}
}
