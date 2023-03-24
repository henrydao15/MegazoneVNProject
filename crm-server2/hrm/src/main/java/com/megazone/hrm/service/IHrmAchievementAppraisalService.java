package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.PO.HrmAchievementAppraisal;
import com.megazone.hrm.entity.PO.HrmAchievementEmployeeAppraisal;
import com.megazone.hrm.entity.VO.*;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmAchievementAppraisalService extends BaseService<HrmAchievementAppraisal> {

	/**
	 * @param setAppraisalBO
	 */
	void addAppraisal(SetAppraisalBO setAppraisalBO);

	/**
	 * @param setAppraisalBO
	 */
	void setAppraisal(SetAppraisalBO setAppraisalBO);

	/**
	 * @param appraisalId id
	 */
	void deleteAppraisal(Integer appraisalId);

	/**
	 * @param appraisalId id
	 */
	void stopAppraisal(Integer appraisalId);


	/**
	 * @param updateAppraisalStatusBO
	 */
	void updateAppraisalStatus(UpdateAppraisalStatusBO updateAppraisalStatusBO);

	/**
	 *
	 */
	Map<Integer, Long> queryAppraisalStatusNum();


	/**
	 * @param queryAppraisalPageListBO
	 * @return
	 */
	BasePage<AppraisalPageListVO> queryAppraisalPageList(QueryAppraisalPageListBO queryAppraisalPageListBO);

	/**
	 * @param appraisalId id
	 * @return
	 */
	AppraisalInformationBO queryAppraisalById(Integer appraisalId);

	/**
	 * @param employeeListByAppraisalIdBO
	 * @return
	 */
	BasePage<EmployeeListByAppraisalIdVO> queryEmployeeListByAppraisalId(QueryEmployeeListByAppraisalIdBO employeeListByAppraisalIdBO);


	/**
	 * @param employeeListBO
	 * @return
	 */
	BasePage<AppraisalEmployeeListVO> queryAppraisalEmployeeList(QueryAppraisalEmployeeListBO employeeListBO);

	/**
	 * @param queryEmployeeAppraisalBO
	 * @return
	 */
	BasePage<EmployeeAppraisalVO> queryEmployeeAppraisal(QueryEmployeeAppraisalBO queryEmployeeAppraisalBO);

	/**
	 * @param employeeAppraisalId id
	 * @return
	 */
	EmployeeAppraisalDetailVO queryEmployeeDetail(Integer employeeAppraisalId);

	/**
	 * @param employeeAppraisal
	 */
	void computeScore(HrmAchievementEmployeeAppraisal employeeAppraisal);


	/**
	 * @param employeeId id
	 */
	Map<String, Object> queryEmployeeAppraisalCount(Integer employeeId);
}
