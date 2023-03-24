package com.megazone.examine.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.crm.entity.ExamineField;
import com.megazone.core.feign.hrm.entity.HrmSalaryMonthRecord;
import com.megazone.core.feign.hrm.service.SalaryRecordService;
import com.megazone.examine.constant.ExamineCodeEnum;
import com.megazone.examine.constant.ExamineConst;
import com.megazone.examine.entity.VO.ExamineFlowConditionDataVO;
import com.megazone.examine.service.ExamineModuleService;
import com.megazone.examine.service.IExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author
 * @date 2020/12/25
 */
@Service("examineHrmService")
public class ExamineHrmServiceImpl implements ExamineModuleService {

	@Autowired
	private SalaryRecordService salaryRecordService;

	@Autowired
	private IExamineService examineService;

	@Override
	public List<ExamineField> queryExamineField(Integer label, Integer categoryId) {
		//year and month actual salary
		List<ExamineField> examineFields = new ArrayList<>();
		examineFields.add(new ExamineField(-1, "payable year", "year", 5, 1));
		examineFields.add(new ExamineField(-1, "Payable Month", "month", 5, 1));
		examineFields.add(new ExamineField(-1, "Real Salary", "realPaySalary", 6, 1));
		return examineFields;
	}

	@Override
	public void updateCheckStatus(Integer label, Integer typeId, Integer checkStatus) {
		salaryRecordService.updateCheckStatus(typeId, checkStatus);
	}

	@Override
	public void checkStatus(Integer label, Integer typeId, Integer checkStatus, Integer oldCheckStatus) {
		if (checkStatus == 4) {
			//The current review has passed irrevocable
			if (oldCheckStatus == 1) {
				throw new CrmException(ExamineCodeEnum.EXAMINE_RECHECK_PASS_ERROR);
			}
		}
	}

	@Override
	public Map<String, Object> getConditionMap(Integer label, Integer typeId, Integer recordId) {
		Map<String, Object> map = new HashMap<>(4);
		List<String> fieldList = new ArrayList<>();
		List<ExamineFlowConditionDataVO> conditionDataVoS = examineService.previewFiledName(label, recordId, null);
		if (conditionDataVoS != null) {
			fieldList = conditionDataVoS.stream().map(ExamineFlowConditionDataVO::getFieldName).collect(Collectors.toList());
			fieldList.removeIf(StrUtil::isEmpty);
		}
		if (CollUtil.isEmpty(fieldList)) {
			return map;
		}
		HrmSalaryMonthRecord hrmSalaryMonthRecord = salaryRecordService.querySalaryRecordById(typeId).getData();
		Map<String, Object> beanMap = BeanUtil.beanToMap(hrmSalaryMonthRecord);
		fieldList.forEach(field -> map.put(field, beanMap.get(field)));
		map.put(ExamineConst.CREATE_USER_ID, hrmSalaryMonthRecord.getCreateUserId());
		return map;
	}
}
