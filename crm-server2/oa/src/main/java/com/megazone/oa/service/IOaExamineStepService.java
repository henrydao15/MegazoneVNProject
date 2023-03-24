package com.megazone.oa.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.PO.OaExamineStep;

public interface IOaExamineStepService extends BaseService<OaExamineStep> {

	OaExamineStep queryExamineStepByNextExamineIdOrderByStepId(Integer categoryId, Integer examineStepId);
}
