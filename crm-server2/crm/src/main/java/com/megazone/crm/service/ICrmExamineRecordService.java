package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.feign.crm.entity.SimpleCrmInfo;
import com.megazone.core.feign.examine.entity.ExamineConditionDataBO;
import com.megazone.core.feign.examine.entity.ExamineMessageBO;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.BO.CrmExamineData;
import com.megazone.crm.entity.PO.CrmExamineRecord;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
public interface ICrmExamineRecordService extends BaseService<CrmExamineRecord> {
	/**
	 * @param type        1，2
	 * @param userId      ID
	 * @param ownerUserId id
	 * @param recordId    id
	 * @param status
	 * @return data
	 */
	public CrmExamineData saveExamineRecord(Integer type, Long userId, Long ownerUserId, Integer recordId, Integer status);

	/**
	 * @param id id
	 */
	public void updateContractMoney(Integer id);

	/**
	 * @param recordId    ID
	 * @param ownerUserId ID
	 */
	public JSONObject queryExamineRecordList(Integer recordId, Long ownerUserId);

	/**
	 * @param recordId ID
	 */
	public List<JSONObject> queryExamineLogList(Integer recordId, String ownerUserId);

	/**
	 * @param recordId   ID
	 * @param status
	 * @param remarks
	 * @param id         id
	 * @param nextUserId
	 */
	public void auditExamine(Integer recordId, Integer status, String remarks, Integer id, Long nextUserId);

	/**
	 * @param categoryType categoryType
	 * @param examineType  1  2  3
	 */
	public void addMessage(Integer categoryType, Integer examineType, Object examineObj, Long ownerUserId);


	/**
	 * @param categoryType categoryType
	 * @param examineType  1  2  3
	 */
	public void addMessageForNewExamine(Integer categoryType, Integer examineType, Object examineObj, Long ownerUserId);

	public void addMessageForNewExamine(ExamineMessageBO examineMessageBO);

	public Map<String, Object> getDataMapForNewExamine(ExamineConditionDataBO examineConditionDataBO);

	public Boolean updateCheckStatusByNewExamine(ExamineConditionDataBO examineConditionDataBO);


	SimpleCrmInfo getCrmSimpleInfo(ExamineConditionDataBO examineConditionDataBO);
}
