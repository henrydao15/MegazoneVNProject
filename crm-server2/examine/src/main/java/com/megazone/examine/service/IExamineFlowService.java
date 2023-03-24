package com.megazone.examine.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.examine.entity.PO.ExamineFlow;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-13
 */
public interface IExamineFlowService extends BaseService<ExamineFlow> {

	/**
	 * @param examineId    ID
	 * @param flowId       ID，null
	 * @param conditionMap ，
	 * @return data
	 */
	public ExamineFlow queryNextExamineFlow(Long examineId, Integer flowId, Map<String, Object> conditionMap);

	/**
	 * @param conditionId ID
	 * @return data
	 */
	public ExamineFlow queryUpperExamineFlow(Integer conditionId, Map<String, Object> conditionMap);
}
