package com.megazone.hrm.common.log;

import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.hrm.entity.PO.HrmSalarySlipTemplate;
import com.megazone.hrm.service.IHrmSalarySlipTemplateService;
import org.springframework.web.bind.annotation.PathVariable;

public class HrmSalarySlipTemplateLog {

	private IHrmSalarySlipTemplateService salarySlipTemplateService = ApplicationContextHolder.getBean(IHrmSalarySlipTemplateService.class);

	public Content deleteSlipTemplate(@PathVariable Integer templateId) {
		HrmSalarySlipTemplate template = salarySlipTemplateService.getById(templateId);
		return new Content(template.getTemplateName(), "deleted" + template.getTemplateName(), BehaviorEnum.DELETE);
	}
}
