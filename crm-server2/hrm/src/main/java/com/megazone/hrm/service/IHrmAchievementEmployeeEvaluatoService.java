package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmAchievementEmployeeEvaluato;
import com.megazone.hrm.entity.VO.EvaluatoResultVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmAchievementEmployeeEvaluatoService extends BaseService<HrmAchievementEmployeeEvaluato> {

	/**
	 * @param employeeAppraisalId
	 * @return
	 */
	List<EvaluatoResultVO> queryEvaluatoList(Integer employeeAppraisalId);
}
