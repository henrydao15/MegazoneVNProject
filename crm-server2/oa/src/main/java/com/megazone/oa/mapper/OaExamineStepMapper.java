package com.megazone.oa.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.oa.entity.PO.OaExamineStep;
import org.apache.ibatis.annotations.Param;

public interface OaExamineStepMapper extends BaseMapper<OaExamineStep> {

	OaExamineStep queryExamineStepByNextExamineIdOrderByStepId(@Param("categoryId") Integer categoryId, @Param("examineStepId") Integer examineStepId);
}
