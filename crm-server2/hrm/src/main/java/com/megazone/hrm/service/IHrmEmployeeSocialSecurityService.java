package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.QuerySalaryListBO;
import com.megazone.hrm.entity.PO.HrmEmployeeSalaryCard;
import com.megazone.hrm.entity.PO.HrmEmployeeSocialSecurityInfo;
import com.megazone.hrm.entity.VO.QuerySalaryListVO;
import com.megazone.hrm.entity.VO.SalaryOptionHeadVO;
import com.megazone.hrm.entity.VO.SalarySocialSecurityVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmEmployeeSocialSecurityService extends BaseService<HrmEmployeeSocialSecurityInfo> {

	/**
	 * @param employeeId
	 * @return
	 */
	SalarySocialSecurityVO salarySocialSecurityInformation(Integer employeeId);

	/**
	 * @param salaryCard
	 */
	void addOrUpdateSalaryCard(HrmEmployeeSalaryCard salaryCard);

	/**
	 * @param salaryCardId
	 */
	void deleteSalaryCard(Integer salaryCardId);

	/**
	 * @param socialSecurityInfo
	 */
	void addOrUpdateSocialSecurity(HrmEmployeeSocialSecurityInfo socialSecurityInfo);

	/**
	 * @param socialSecurityInfoId
	 */
	void deleteSocialSecurity(Integer socialSecurityInfoId);

	/**
	 * @param querySalaryListBO
	 * @return
	 */
	BasePage<QuerySalaryListVO> querySalaryList(QuerySalaryListBO querySalaryListBO);

	/**
	 * @param sEmpRecordId
	 * @return
	 */
	List<SalaryOptionHeadVO> querySalaryDetail(String sEmpRecordId);
}
