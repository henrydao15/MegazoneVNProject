package com.megazone.core.feign.crm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.feign.crm.entity.CrmExamineData;
import com.megazone.core.feign.crm.entity.CrmSaveExamineRecordBO;
import com.megazone.core.feign.crm.entity.SimpleCrmInfo;
import com.megazone.core.feign.crm.service.impl.FeignCrmExamineServiceImpl;
import com.megazone.core.feign.examine.entity.ExamineConditionDataBO;
import com.megazone.core.feign.examine.entity.ExamineMessageBO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(name = "crm", contextId = "examine", fallback = FeignCrmExamineServiceImpl.class)
public interface CrmExamineService {

	@PostMapping("/crmExamineRecord/saveExamineRecord")
	@ApiModelProperty
	Result<CrmExamineData> saveExamineRecord(@RequestBody CrmSaveExamineRecordBO examineRecordBO);

	@PostMapping("/crmExamineRecord/queryByRecordId")
	Result<List<JSONObject>> queryByRecordId(@RequestParam("recordId") Integer recordId);

	@PostMapping("/crmExamine/queryExamineStepIsExist")
	Result<Boolean> queryExamineStepIsExist(@RequestParam("categoryType") Integer categoryType);

	@PostMapping("/crmExamineRecord/queryExamineRecordList")
	Result<JsonNode> queryExamineRecordList(
			@RequestParam("recordId") Integer recordId,
			@RequestParam("ownerUserId") Long ownerUserId);


	@PostMapping("/crmExamineRecord/queryConditionData")
	Result<Map<String, Object>> getDataMapForNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO);


	@PostMapping("/crmExamineRecord/updateCheckStatusByNewExamine")
	Result<Boolean> updateCheckStatusByNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO);

	@PostMapping("/crmExamineRecord/addMessageForNewExamine")
	Result addMessageForNewExamine(@RequestBody ExamineMessageBO examineMessageBO);


	@PostMapping("/crmExamineRecord/getCrmSimpleInfo")
	Result<SimpleCrmInfo> getCrmSimpleInfo(@RequestBody ExamineConditionDataBO examineConditionDataBO);
}
