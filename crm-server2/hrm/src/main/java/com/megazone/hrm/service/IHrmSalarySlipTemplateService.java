package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.AddSlipTemplateBO;
import com.megazone.hrm.entity.PO.HrmSalarySlipTemplate;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
public interface IHrmSalarySlipTemplateService extends BaseService<HrmSalarySlipTemplate> {

	/**
	 * @param addSlipTemplateBO
	 */
	void addSlipTemplate(AddSlipTemplateBO addSlipTemplateBO);

	/**
	 * @param templateId
	 */
	void deleteSlipTemplate(Integer templateId);
}
