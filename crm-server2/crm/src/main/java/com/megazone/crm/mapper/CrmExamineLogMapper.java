package com.megazone.crm.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.PO.CrmExamineLog;
import com.megazone.crm.entity.PO.CrmExamineStep;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 * TODO ，JSONObject,
 *
 * @author
 * @since 2020-05-28
 */
public interface CrmExamineLogMapper extends BaseMapper<CrmExamineLog> {

	public List<JSONObject> queryUserByRecordId(@Param("recordId") Integer recordId, @Param("status") Integer status);

	public List<JSONObject> queryRecordByUserIdAndStatus(@Param("examineTime") Date examineTime, @Param("userId") Long userId);

	public JSONObject queryRecordAndId(@Param("recordId") Integer recordId);

	public List<JSONObject> queryExamineLogAndUserByRecordId(@Param("recordId") Integer recordId);

	public JSONObject queryExamineLogAndUserByLogId(@Param("id") Integer id);

	public JSONObject queryExamineLog(@Param("id") Long userId, @Param("recordId") Integer recordId, @Param("stepId") Long stepId);

	public List<JSONObject> queryUserByRecordIdAndStepIdAndStatus(@Param("recordId") Integer recordId, @Param("stepId") Long stepId);

	public List<JSONObject> queryUserByUserId(@Param("id") Long userId);

	public List<JSONObject> queryUserByUserIdAnd(@Param("id") Long userId);

	public List<JSONObject> queryExamineLogByRecordIdByStep(@Param("recordId") Integer recordId);

	public CrmExamineLog queryNowadayExamineLogByRecordIdAndStepId(@Param("recordId") Integer recordId, @Param("stepId") Long stepId, @Param("userId") Long userId);

	public CrmExamineLog queryNowadayExamineLogByRecordIdAndStatus(@Param("recordId") Integer recordId, @Param("userId") Long userId);

	public Integer queryCountByStepId(@Param("recordId") Integer recordId, @Param("stepId") Long stepId);

	public CrmExamineStep queryExamineStepByNextExamineIdOrderByStepId(@Param("examineId") Integer examineId, @Param("stepId") Long stepId);

	List<JSONObject> queryByRecordIdAndStatus(@Param("recordId") Integer recordId);
}
