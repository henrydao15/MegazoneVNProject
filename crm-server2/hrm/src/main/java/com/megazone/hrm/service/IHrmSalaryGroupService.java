package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.SetSalaryGroupBO;
import com.megazone.hrm.entity.PO.HrmSalaryGroup;
import com.megazone.hrm.entity.PO.HrmSalaryTaxRule;
import com.megazone.hrm.entity.VO.SalaryGroupPageListVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface IHrmSalaryGroupService extends BaseService<HrmSalaryGroup> {

	/**
	 * @param pageEntity
	 * @return
	 */
	BasePage<SalaryGroupPageListVO> querySalaryGroupPageList(PageEntity pageEntity);

	/**
	 * @param employeeId
	 * @return
	 */
	HrmSalaryTaxRule queryEmployeeTaxRule(Integer employeeId);

	/**
	 * @param salaryGroup
	 */
	void setSalaryGroup(SetSalaryGroupBO salaryGroup);

	/**
	 * @param taxType
	 * @return
	 */
	List<HrmSalaryGroup> querySalaryGroupByTaxType(int taxType);
}
