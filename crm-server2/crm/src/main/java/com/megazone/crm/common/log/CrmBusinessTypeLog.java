package com.megazone.crm.common.log;

import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.crm.service.ICrmBusinessTypeService;
import org.springframework.web.bind.annotation.RequestParam;

public class CrmBusinessTypeLog {
	private ICrmBusinessTypeService crmBusinessTypeService = ApplicationContextHolder.getBean(ICrmBusinessTypeService.class);

	public Content deleteById(Integer typeId) {
		String businessTypeName = crmBusinessTypeService.getBusinessTypeName(typeId);
		crmBusinessTypeService.deleteById(typeId);
		return new Content(businessTypeName, "Deleted business group:" + businessTypeName, BehaviorEnum.DELETE);
	}

	public Content updateStatus(@RequestParam("typeId") Integer typeId, @RequestParam("status") Integer status) {
		String detail;
		if (status == 0) {
			detail = "Disabled business group:";
		} else {
			detail = "Business group enabled:";
		}
		String businessTypeName = crmBusinessTypeService.getBusinessTypeName(typeId);
		return new Content(businessTypeName, detail + businessTypeName, BehaviorEnum.DELETE);
	}
}
