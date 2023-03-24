package com.megazone.oa.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.oa.entity.PO.OaExamineStep;
import com.megazone.oa.mapper.OaExamineStepMapper;
import com.megazone.oa.service.IOaExamineStepService;
import org.springframework.stereotype.Service;

@Service
public class OaExamineStepServiceImpl extends BaseServiceImpl<OaExamineStepMapper, OaExamineStep> implements IOaExamineStepService {


	@Override
	public OaExamineStep queryExamineStepByNextExamineIdOrderByStepId(Integer categoryId, Integer examineStepId) {
		return getBaseMapper().queryExamineStepByNextExamineIdOrderByStepId(categoryId, examineStepId);
	}
}
