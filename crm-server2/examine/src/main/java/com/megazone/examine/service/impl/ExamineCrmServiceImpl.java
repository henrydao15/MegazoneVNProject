package com.megazone.examine.service.impl;

import cn.hutool.core.util.StrUtil;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.crm.entity.ExamineField;
import com.megazone.core.feign.crm.service.CrmExamineService;
import com.megazone.core.feign.crm.service.CrmService;
import com.megazone.core.feign.examine.entity.ExamineConditionDataBO;
import com.megazone.examine.constant.ExamineCodeEnum;
import com.megazone.examine.entity.VO.ExamineFlowConditionDataVO;
import com.megazone.examine.service.ExamineModuleService;
import com.megazone.examine.service.IExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("examineCrmService")
public class ExamineCrmServiceImpl implements ExamineModuleService {

	@Autowired
	private IExamineService examineService;

	@Autowired
	private CrmService crmService;

	@Autowired
	private CrmExamineService crmExamineService;

	/**
	 * Query the fields available for approval
	 *
	 * @param label      label
	 * @param categoryId categoryId
	 * @return data
	 */
	@Override
	public List<ExamineField> queryExamineField(Integer label, Integer categoryId) {
		if (label != 3) {
			List<ExamineField> examineFields = crmService.queryExamineField(label).getData();
			examineFields.forEach(examineField -> examineField.setFieldName(StrUtil.toCamelCase(examineField.getFieldName())));
			return examineFields;
		} else {
			//The invoice condition field is processed by the front-end [Invoicing Amount], [Invoicing Type]
			List<ExamineField> examineFields = new ArrayList<>();
			examineFields.add(new ExamineField(-1, "Invoice Amount", "invoiceMoney", 6, 1));
			return examineFields;
		}
	}

	/**
	 * Modify review status
	 *
	 * @param label       type
	 * @param typeId      corresponds to the type primary key ID
	 * @param checkStatus Review status 0 to be reviewed, 1 to pass, 2 to reject, 3 to be reviewed 4: withdraw 5 not submitted 6 created 7 deleted 8 void
	 */
	@Override
	public void updateCheckStatus(Integer label, Integer typeId, Integer checkStatus) {
		ExamineConditionDataBO examineConditionDataBO = new ExamineConditionDataBO();
		examineConditionDataBO.setLabel(label);
		examineConditionDataBO.setTypeId(typeId);
		examineConditionDataBO.setCheckStatus(checkStatus);
		crmExamineService.updateCheckStatusByNewExamine(examineConditionDataBO);
	}

	/**
	 * Check whether the current state can be performed, and it is not possible to directly throw an exception
	 *
	 * @param label          type
	 * @param typeId         corresponds to the type ID
	 * @param checkStatus    review status
	 * @param oldCheckStatus previous audit status
	 */
	@Override
	public void checkStatus(Integer label, Integer typeId, Integer checkStatus, Integer oldCheckStatus) {
		if (checkStatus == 4) {
			//The current review has passed irrevocable
			if (oldCheckStatus == 1) {
				throw new CrmException(ExamineCodeEnum.EXAMINE_RECHECK_PASS_ERROR);
			}
		}
	}

	/**
	 * Get the data entity required for conditional review
	 *
	 * @param label  type
	 * @param typeId corresponds to the type ID
	 * @return map
	 */
	@Override
	public Map<String, Object> getConditionMap(Integer label, Integer typeId, Integer recordId) {
		ExamineConditionDataBO examineConditionDataBO = new ExamineConditionDataBO();
		examineConditionDataBO.setLabel(label);
		examineConditionDataBO.setTypeId(typeId);
		List<String> fieldList = new ArrayList<>();
		List<ExamineFlowConditionDataVO> conditionDataVoS = examineService.previewFiledName(label, recordId, null);
		if (conditionDataVoS != null) {
			fieldList = conditionDataVoS.stream().map(ExamineFlowConditionDataVO::getFieldName).collect(Collectors.toList());
			fieldList.removeIf(StrUtil::isEmpty);
		}
		examineConditionDataBO.setFieldList(fieldList);
		return crmExamineService.getDataMapForNewExamine(examineConditionDataBO).getData();
	}


}
