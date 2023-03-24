package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.entity.PO.HrmAchievementEmployeeEvaluato;
import com.megazone.hrm.entity.VO.EvaluatoResultVO;
import com.megazone.hrm.mapper.HrmAchievementEmployeeEvaluatoMapper;
import com.megazone.hrm.service.IHrmAchievementEmployeeEvaluatoService;
import com.megazone.hrm.service.IHrmEmployeeService;
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
public class HrmAchievementEmployeeEvaluatoServiceImpl extends BaseServiceImpl<HrmAchievementEmployeeEvaluatoMapper, HrmAchievementEmployeeEvaluato> implements IHrmAchievementEmployeeEvaluatoService {


	@Autowired
	private IHrmEmployeeService iHrmEmployeeService;

	@Autowired
	private HrmAchievementEmployeeEvaluatoMapper evaluatoMapper;

	@Override
	public List<EvaluatoResultVO> queryEvaluatoList(Integer employeeAppraisalId) {
		return evaluatoMapper.queryEvaluatoList(employeeAppraisalId);
	}
}
