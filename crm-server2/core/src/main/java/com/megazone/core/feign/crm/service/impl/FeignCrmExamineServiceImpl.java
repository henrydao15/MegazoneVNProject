package com.megazone.core.feign.crm.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.feign.crm.entity.CrmExamineData;
import com.megazone.core.feign.crm.entity.CrmSaveExamineRecordBO;
import com.megazone.core.feign.crm.entity.SimpleCrmInfo;
import com.megazone.core.feign.crm.service.CrmExamineService;
import com.megazone.core.feign.examine.entity.ExamineConditionDataBO;
import com.megazone.core.feign.examine.entity.ExamineMessageBO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FeignCrmExamineServiceImpl implements CrmExamineService {
	@Override
	public Result<CrmExamineData> saveExamineRecord(CrmSaveExamineRecordBO examineRecordBO) {
		return null;
	}

	@Override
	public Result<List<JSONObject>> queryByRecordId(Integer recordId) {
		return null;
	}

	@Override
	public Result<Boolean> queryExamineStepIsExist(Integer categoryType) {
		return null;
	}

	@Override
	public Result<JsonNode> queryExamineRecordList(Integer recordId, Long ownerUserId) {
		return null;
	}

	@Override
	public Result<Map<String, Object>> getDataMapForNewExamine(ExamineConditionDataBO examineConditionDataBO) {
		return Result.ok(null);
	}

	@Override
	public Result<Boolean> updateCheckStatusByNewExamine(ExamineConditionDataBO examineConditionDataBO) {
		return Result.ok(false);
	}

	@Override
	public Result addMessageForNewExamine(ExamineMessageBO examineMessageBO) {
		return Result.ok();
	}

	@Override
	public Result<SimpleCrmInfo> getCrmSimpleInfo(ExamineConditionDataBO examineConditionDataBO) {
		return Result.ok(null);
	}
}
