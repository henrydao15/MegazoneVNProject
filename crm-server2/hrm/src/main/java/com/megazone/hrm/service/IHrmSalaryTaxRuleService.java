package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.SetTaxRuleBO;
import com.megazone.hrm.entity.PO.HrmSalaryTaxRule;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface IHrmSalaryTaxRuleService extends BaseService<HrmSalaryTaxRule> {

	/**
	 * @return
	 */
	List<HrmSalaryTaxRule> queryTaxRuleList();

	/**
	 * @param setTaxRuleBO
	 */
	void setTaxRule(SetTaxRuleBO setTaxRuleBO);

}
