package com.megazone.oa.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.oa.entity.PO.OaExamineLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OaExamineLogMapper extends BaseMapper<OaExamineLog> {

	OaExamineLog queryExamineLog(@Param("recordId") Integer examineRecordId, @Param("examineUser") Long userId, @Param("stepId") Integer examineStepId);

	Integer queryCountByStepId(@Param("recordId") Integer recordId, @Param("stepId") Integer stepId);

	JSONObject queryUserByRecordId(@Param("recordId") String recordId);

	JSONObject queryRecordAndId(String recordId);

	JSONObject queryRecordByUserIdAndStatus(@Param("createUser") Long createUser, @Param("examineTime") Date examineTime);

	List<JSONObject> queryExamineLogAndUserByRecordId(String recordId);

	JSONObject queryExamineLogAndUserByLogId(Integer logId);

	List<JSONObject> queryUserByRecordIdAndStepIdAndStatus(@Param("recordId") String recordId, @Param("stepId") long stepId);

	List<JSONObject> queryUserByUserId(Long createUserId);

	JSONObject queryUserByUserIdAndStatus(Long userId);

	List<JSONObject> queryExamineLogByRecordIdByStep1(Integer recordId);

	List<JSONObject> queryExamineLogByRecordIdByStep(Integer recordId);
}
