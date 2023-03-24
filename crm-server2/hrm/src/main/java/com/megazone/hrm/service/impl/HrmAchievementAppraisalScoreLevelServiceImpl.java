package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.entity.PO.HrmAchievementAppraisalScoreLevel;
import com.megazone.hrm.entity.PO.HrmAchievementEmployeeAppraisal;
import com.megazone.hrm.mapper.HrmAchievementAppraisalScoreLevelMapper;
import com.megazone.hrm.service.IHrmAchievementAppraisalScoreLevelService;
import com.megazone.hrm.service.IHrmAchievementEmployeeAppraisalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Service
public class HrmAchievementAppraisalScoreLevelServiceImpl extends BaseServiceImpl<HrmAchievementAppraisalScoreLevelMapper, HrmAchievementAppraisalScoreLevel> implements IHrmAchievementAppraisalScoreLevelService {

	@Autowired
	private IHrmAchievementEmployeeAppraisalService employeeAppraisalService;

	@Override
	public List<HrmAchievementAppraisalScoreLevel> queryScoreLevelList(Integer employeeAppraisalId) {
		HrmAchievementEmployeeAppraisal employeeAppraisal = employeeAppraisalService.getById(employeeAppraisalId);
		return lambdaQuery().eq(HrmAchievementAppraisalScoreLevel::getAppraisalId, employeeAppraisal.getAppraisalId()).list();
	}

	@Override
	public List<HrmAchievementAppraisalScoreLevel> queryScoreLevelListByAppraisalId(Integer appraisalId) {
		return lambdaQuery().eq(HrmAchievementAppraisalScoreLevel::getAppraisalId, appraisalId).list();
	}
}
