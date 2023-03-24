package com.megazone.crm.common.log;

import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmFieldBO;

public class CrmFieldLog {

	public Content saveField(CrmFieldBO crmFieldBO) {
		CrmEnum crmEnum = CrmEnum.parse(crmFieldBO.getLabel());
		return new Content(crmEnum.getRemarks(), "saved custom fields:" + crmEnum.getRemarks(), BehaviorEnum.SAVE);
	}
}
