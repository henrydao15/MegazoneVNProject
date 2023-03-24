package com.megazone.examine.service;


import com.megazone.core.feign.crm.entity.ExamineField;

import java.util.List;
import java.util.Map;

public interface ExamineModuleService {

	/**
	 * @param label      label
	 * @param categoryId categoryId
	 * @return data
	 */
	public List<ExamineField> queryExamineField(Integer label, Integer categoryId);

	/**
	 * @param label
	 * @param typeId      ID
	 * @param checkStatus 0、1、2、3 4: 5  6  7  8
	 */
	public void updateCheckStatus(Integer label, Integer typeId, Integer checkStatus);

	/**
	 * @param label
	 * @param typeId         ID
	 * @param checkStatus
	 * @param oldCheckStatus
	 */
	public void checkStatus(Integer label, Integer typeId, Integer checkStatus, Integer oldCheckStatus);

	/**
	 * @param label
	 * @param typeId ID
	 * @return map
	 */
	public Map<String, Object> getConditionMap(Integer label, Integer typeId, Integer recordId);
}
