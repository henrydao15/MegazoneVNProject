package com.megazone.hrm.common.log;

import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.hrm.entity.PO.HrmSalaryChangeTemplate;
import com.megazone.hrm.service.IHrmSalaryChangeTemplateService;

public class HrmSalaryChangeTemplateLog {

	private IHrmSalaryChangeTemplateService salaryChangeTemplateService = ApplicationContextHolder.getBean(IHrmSalaryChangeTemplateService.class);

	public Content deleteChangeTemplate(Integer id) {
		HrmSalaryChangeTemplate template = salaryChangeTemplateService.getById(id);
		salaryChangeTemplateService.deleteChangeTemplate(id);
		return new Content(template.getTemplateName(), "template deleted" + template.getTemplateName(), BehaviorEnum.DELETE);
	}
}
