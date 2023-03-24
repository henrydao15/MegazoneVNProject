package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.SetInterviewResultBO;
import com.megazone.hrm.entity.BO.SetRecruitInterviewBO;
import com.megazone.hrm.entity.PO.HrmRecruitInterview;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmRecruitInterviewService extends BaseService<HrmRecruitInterview> {

	/**
	 * @param setRecruitInterviewBO
	 */
	void setInterview(SetRecruitInterviewBO setRecruitInterviewBO);

	/**
	 * @param setInterviewResultBO
	 */
	void setInterviewResult(SetInterviewResultBO setInterviewResultBO);

	/**
	 * @param setRecruitInterviewBO
	 */
	void addBatchInterview(SetRecruitInterviewBO setRecruitInterviewBO);
}
