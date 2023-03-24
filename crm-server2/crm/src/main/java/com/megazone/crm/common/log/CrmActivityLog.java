package com.megazone.crm.common.log;

import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.CrmActivity;

public class CrmActivityLog {
	private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);


	public Content addCrmActivityRecord(CrmActivity crmActivity) {
		CrmEnum crmEnum = CrmEnum.parse(crmActivity.getActivityType());
		return new Content(crmEnum.getRemarks(), crmEnum.getRemarks(), "New follow-up record");
	}
}
