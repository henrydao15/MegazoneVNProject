package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
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
public interface HrmAchievementEmployeeEvaluatoMapper extends BaseMapper<HrmAchievementEmployeeEvaluato> {

	List<EvaluatoResultVO> queryEvaluatoList(Integer employeeAppraisalId);
}
