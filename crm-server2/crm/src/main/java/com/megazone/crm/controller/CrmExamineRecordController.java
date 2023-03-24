package com.megazone.crm.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.feign.crm.entity.CrmSaveExamineRecordBO;
import com.megazone.core.feign.crm.entity.SimpleCrmInfo;
import com.megazone.core.feign.examine.entity.ExamineConditionDataBO;
import com.megazone.core.feign.examine.entity.ExamineMessageBO;
import com.megazone.crm.entity.BO.CrmAuditExamineBO;
import com.megazone.crm.entity.BO.CrmExamineData;
import com.megazone.crm.service.ICrmExamineLogService;
import com.megazone.crm.service.ICrmExamineRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RestController
@RequestMapping("/crmExamineRecord")
@Api(tags = "")
public class CrmExamineRecordController {

	@Autowired
	private ICrmExamineRecordService examineRecordService;

	@Autowired
	private ICrmExamineLogService examineLogService;


	@PostMapping("/saveExamineRecord")
	@ApiModelProperty
	public Result<CrmExamineData> saveExamineRecord(@RequestBody CrmSaveExamineRecordBO examineRecordBO) {
		CrmExamineData crmExamineData = examineRecordService.saveExamineRecord(examineRecordBO.getType(), examineRecordBO.getUserId(), examineRecordBO.getOwnerUserId(), examineRecordBO.getRecordId(), examineRecordBO.getStatus());
		return Result.ok(crmExamineData);
	}

	@PostMapping("/queryByRecordId")
	@ApiModelProperty
	public Result<List<JSONObject>> queryByRecordId(@ApiParam("ID") @RequestParam("recordId") Integer recordId) {
		return Result.ok(examineLogService.queryByRecordId(recordId));
	}

	@PostMapping("/queryExamineRecordList")
	@ApiOperation("")
	public Result<JsonNode> queryExamineRecordList(
			@ApiParam("ID") @RequestParam("recordId") Integer recordId,
			@ApiParam("ID") @RequestParam("ownerUserId") Long ownerUserId) {
		JSONObject object = examineRecordService.queryExamineRecordList(recordId, ownerUserId);
		return Result.ok(object.node);
	}

	@PostMapping("/queryExamineLogList")
	@ApiOperation("")
	public Result<List<JSONObject>> queryExamineLogList(@ApiParam("ID") @RequestParam(value = "ownerUserId", required = false) String ownerUserId, @ApiParam("ID") @RequestParam("recordId") Integer recordId) {
		List<JSONObject> object = examineRecordService.queryExamineLogList(recordId, ownerUserId);
		return Result.ok(object);
	}

	@PostMapping("/auditExamine")
	@ApiOperation("")
	public Result auditExamine(@RequestBody CrmAuditExamineBO auditExamine) {
		examineRecordService.auditExamine(auditExamine.getRecordId(), auditExamine.getStatus(), auditExamine.getRemarks(), auditExamine.getId(), auditExamine.getNextUserId());
		return Result.ok();
	}


	@PostMapping("/queryConditionData")
	@ApiExplain("")
	public Result<Map<String, Object>> getDataMapForNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO) {
		return Result.ok(examineRecordService.getDataMapForNewExamine(examineConditionDataBO));
	}


	@PostMapping("/updateCheckStatusByNewExamine")
	@ApiExplain("")
	public Result<Boolean> updateCheckStatusByNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO) {
		return Result.ok(examineRecordService.updateCheckStatusByNewExamine(examineConditionDataBO));
	}

	@PostMapping("/addMessageForNewExamine")
	@ApiExplain("")
	public Result addMessageForNewExamine(@RequestBody ExamineMessageBO examineMessageBO) {
		examineRecordService.addMessageForNewExamine(examineMessageBO);
		return Result.ok();
	}


	@PostMapping("/getCrmSimpleInfo")
	@ApiExplain("")
	public Result<SimpleCrmInfo> getCrmSimpleInfo(@RequestBody ExamineConditionDataBO examineConditionDataBO) {
		return Result.ok(examineRecordService.getCrmSimpleInfo(examineConditionDataBO));
	}
}

