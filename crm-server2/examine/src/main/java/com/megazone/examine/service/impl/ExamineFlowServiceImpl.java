package com.megazone.examine.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.examine.constant.ExamineTypeEnum;
import com.megazone.examine.entity.PO.ExamineCondition;
import com.megazone.examine.entity.PO.ExamineFlow;
import com.megazone.examine.mapper.ExamineFlowMapper;
import com.megazone.examine.service.IExamineConditionService;
import com.megazone.examine.service.IExamineFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-13
 */
@Service
public class ExamineFlowServiceImpl extends BaseServiceImpl<ExamineFlowMapper, ExamineFlow> implements IExamineFlowService {

	@Autowired
	private IExamineConditionService examineConditionService;

	/**
	 * @param examineId    ID
	 * @param flowId       ID，null
	 * @param conditionMap ，
	 * @return data
	 */
	@Override
	public ExamineFlow queryNextExamineFlow(Long examineId, Integer flowId, Map<String, Object> conditionMap) {
		/*

		 */
		if (flowId == null) {
			ExamineFlow examineFlow = lambdaQuery().eq(ExamineFlow::getExamineId, examineId).ge(ExamineFlow::getSort, 0).orderByAsc(ExamineFlow::getSort).last(" limit 1").one();
			if (examineFlow == null) {
				return null;
			}
			if (!ExamineTypeEnum.CONDITION.getType().equals(examineFlow.getExamineType())) {
				return examineFlow;
			}
			return examineConditionService.queryNextExamineFlowByCondition(examineFlow, conditionMap);
		}

		/*

		 */
		ExamineFlow examineFlow = getById(flowId);
//        if (examineFlow.getExamineType().equals(ExamineTypeEnum.CONDITION.getType())) {
//            return examineConditionService.queryNextExamineFlowByCondition(examineFlow, conditionMap);
//        }
        /*
         ，
         */
		ExamineFlow nextExamineFlow = lambdaQuery()
				.eq(ExamineFlow::getExamineId, examineFlow.getExamineId())
				.eq(ExamineFlow::getConditionId, examineFlow.getConditionId())
				.gt(ExamineFlow::getSort, examineFlow.getSort())
				.orderByAsc(ExamineFlow::getSort)
				.last(" limit 1").one();

		if (nextExamineFlow != null && examineFlow.getExamineType().equals(ExamineTypeEnum.CONDITION.getType())) {
			return nextExamineFlow;
		}

		if (nextExamineFlow == null && examineFlow.getConditionId() != 0) {
            /*
             ，
             */
			nextExamineFlow = queryUpperExamineFlow(examineFlow.getConditionId(), conditionMap);
		}
		/*

		 */
		if (nextExamineFlow != null && Objects.equals(nextExamineFlow.getExamineType(), ExamineTypeEnum.CONDITION.getType())) {
			return examineConditionService.queryNextExamineFlowByCondition(nextExamineFlow, conditionMap);
		}
		return nextExamineFlow;
	}


	/**
	 * @param conditionId ID
	 * @return data
	 */
	public ExamineFlow queryUpperExamineFlow(Integer conditionId, Map<String, Object> conditionMap) {
		ExamineCondition condition = examineConditionService.getById(conditionId);
		ExamineFlow examineFlow = getById(condition.getFlowId());
        /*
         ，
         */
		ExamineFlow nextExamineFlow = lambdaQuery()
				.eq(ExamineFlow::getExamineId, examineFlow.getExamineId())
				.eq(ExamineFlow::getConditionId, examineFlow.getConditionId())
				.gt(ExamineFlow::getSort, examineFlow.getSort())
				.last(" limit 1").one();
		if (nextExamineFlow != null && Objects.equals(examineFlow.getExamineType(), ExamineTypeEnum.CONDITION.getType())) {
			return nextExamineFlow;
		}
        /*
          ，ID，，
         */
		if (nextExamineFlow == null && examineFlow.getConditionId() != 0) {
            /*
             ，
             */
			return queryUpperExamineFlow(examineFlow.getConditionId(), conditionMap);
		}
		/*

		 */
		if (nextExamineFlow != null && Objects.equals(nextExamineFlow.getExamineType(), ExamineTypeEnum.CONDITION.getType())) {
			return examineConditionService.queryNextExamineFlowByCondition(nextExamineFlow, conditionMap);
		}
		return nextExamineFlow;
	}
}
