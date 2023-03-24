package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.PO.HrmAchievementEmployeeAppraisal;
import com.megazone.hrm.entity.VO.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmAchievementEmployeeAppraisalService extends BaseService<HrmAchievementEmployeeAppraisal> {

	/**
	 * @return
	 */
	Map<Integer, Integer> queryAppraisalNum();

	/**
	 * @param basePageBO
	 * @return
	 */
	BasePage<QueryMyAppraisalVO> queryMyAppraisal(BasePageBO basePageBO);

	/**
	 * @param basePageBO
	 * @return
	 */
	BasePage<TargetConfirmListVO> queryTargetConfirmList(BasePageBO basePageBO);

	/**
	 * @param evaluatoListBO
	 * @return
	 */
	BasePage<EvaluatoListVO> queryEvaluatoList(EvaluatoListBO evaluatoListBO);

	/**
	 * @param employeeAppraisalId
	 * @return
	 */
	EmployeeAppraisalDetailVO queryEmployeeAppraisalDetail(Integer employeeAppraisalId);

	/**
	 * @param writeAppraisalBO
	 */
	void writeAppraisal(WriteAppraisalBO writeAppraisalBO);

	/**
	 * @param targetConfirmBO
	 */
	void targetConfirm(TargetConfirmBO targetConfirmBO);

	/**
	 * @param resultEvaluatoBO
	 */
	void resultEvaluato(ResultEvaluatoBO resultEvaluatoBO);

	/**
	 * @param basePageBO
	 * @return
	 */
	BasePage<ResultConfirmListVO> queryResultConfirmList(BasePageBO basePageBO);

	/**
	 * @param appraisalId
	 * @return
	 */
	ResultConfirmByAppraisalIdVO queryResultConfirmByAppraisalId(String appraisalId);

	/**
	 * @param updateScoreLevelBO
	 */
	void updateScoreLevel(UpdateScoreLevelBO updateScoreLevelBO);

	/**
	 * @param appraisalId
	 */
	void resultConfirm(String appraisalId);

	/**
	 * @param updateScheduleBO
	 */
	void updateSchedule(UpdateScheduleBO updateScheduleBO);

	/**
	 * @return
	 */

	List<AchievementAppraisalVO> queryTargetConfirmScreen(Integer employeeId, Integer status);

	/**
	 * @return
	 */
	List<AchievementAppraisalVO> queryEvaluatoScreen(Integer employeeId, Integer status);

	/**
	 * @param queryLevelIdByScoreBO
	 * @return
	 */
	Integer queryLevelIdByScore(QueryLevelIdByScoreBO queryLevelIdByScoreBO);


}
