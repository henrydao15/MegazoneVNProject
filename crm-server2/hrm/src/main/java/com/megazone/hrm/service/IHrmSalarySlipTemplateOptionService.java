package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmSalarySlipTemplateOption;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
public interface IHrmSalarySlipTemplateOptionService extends BaseService<HrmSalarySlipTemplateOption> {

	/**
	 * @param templateId
	 * @return
	 */
	List<HrmSalarySlipTemplateOption> queryTemplateOptionByTemplateId(Integer templateId);
}
