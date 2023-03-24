package com.megazone.examine.service;

import com.megazone.core.entity.UserInfo;
import com.megazone.core.servlet.BaseService;
import com.megazone.examine.entity.PO.ExamineCondition;
import com.megazone.examine.entity.PO.ExamineConditionData;
import com.megazone.examine.entity.PO.ExamineFlow;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-13
 */
public interface IExamineConditionService extends BaseService<ExamineCondition> {

	/**
	 * @param examineFlow
	 * @param conditionMap entity
	 * @return data
	 */
	public ExamineFlow queryNextExamineFlowByCondition(ExamineFlow examineFlow, Map<String, Object> conditionMap);

	/**
	 * @param conditionDataList
	 * @param conditionMap
	 * @param userInfo
	 * @return boolean
	 * @date 2020/12/16 13:18
	 **/
	public boolean handleExamineConditionData(List<ExamineConditionData> conditionDataList, Map<String, Object> conditionMap, UserInfo userInfo);

}
