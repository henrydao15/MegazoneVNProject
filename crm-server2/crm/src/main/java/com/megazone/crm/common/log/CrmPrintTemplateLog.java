package com.megazone.crm.common.log;

import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.crm.entity.PO.CrmPrintTemplate;
import com.megazone.crm.service.ICrmPrintTemplateService;

public class CrmPrintTemplateLog {


	private ICrmPrintTemplateService printTemplateService = ApplicationContextHolder.getBean(ICrmPrintTemplateService.class);

	public Content deletePrintTemplate(Integer templateId) {
		CrmPrintTemplate template = printTemplateService.getById(templateId);
		return new Content(template.getTemplateName(), "deleted print template:" + template.getTemplateName(), BehaviorEnum.DELETE);
	}

	public Content copyTemplate(Integer templateId) {
		CrmPrintTemplate template = printTemplateService.getById(templateId);
		return new Content(template.getTemplateName(), "Copy the print template:" + template.getTemplateName(), BehaviorEnum.COPY);
	}
}
