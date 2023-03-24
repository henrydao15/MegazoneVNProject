package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmAchievementAppraisalScoreLevel;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmAchievementAppraisalScoreLevelService extends BaseService<HrmAchievementAppraisalScoreLevel> {


	/**
	 * @param employeeAppraisalId id
	 */
	List<HrmAchievementAppraisalScoreLevel> queryScoreLevelList(Integer employeeAppraisalId);

	/**
	 * @param appraisalId id
	 */
	List<HrmAchievementAppraisalScoreLevel> queryScoreLevelListByAppraisalId(Integer appraisalId);
}
