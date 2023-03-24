package com.megazone.examine.controller;


import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.Result;
import com.megazone.core.feign.examine.entity.ExamineRecordReturnVO;
import com.megazone.core.feign.examine.entity.ExamineRecordSaveBO;
import com.megazone.examine.entity.BO.ExamineBO;
import com.megazone.examine.entity.PO.ExamineRecordLog;
import com.megazone.examine.entity.VO.ExamineRecordLogVO;
import com.megazone.examine.entity.VO.ExamineRecordVO;
import com.megazone.examine.service.IExamineRecordLogService;
import com.megazone.examine.service.IExamineRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-19
 */
@RestController
@RequestMapping("/examineRecord")
public class ExamineRecordController {

	@Autowired
	private IExamineRecordService examineRecordService;

	@Autowired
	private IExamineRecordLogService examineRecordLogService;

	@PostMapping("/addExamineRecord")
	@ApiExplain("")
	public Result<ExamineRecordReturnVO> addExamineRecord(@RequestBody ExamineRecordSaveBO examineRecordSaveBO) {
		ExamineRecordReturnVO examineRecordVO = examineRecordService.addExamineRecord(examineRecordSaveBO);
		return Result.ok(examineRecordVO);
	}

	@PostMapping("/auditExamine")
	@ApiOperation("")
	public Result auditExamine(@RequestBody ExamineBO examineBO) {
		examineRecordService.auditExamine(examineBO);
		return Result.ok();
	}

	@PostMapping("/queryExamineRecordLog")
	@ApiOperation("")
	public Result<List<ExamineRecordLogVO>> queryExamineRecord(@RequestParam(value = "ownerUserId", required = false) String ownerUserId, @RequestParam("recordId") Integer recordId) {
		return Result.ok(examineRecordLogService.queryExamineRecordLog(recordId, ownerUserId));
	}


	@PostMapping("/queryExamineRecordInfo")
	@ApiOperation("")
	public Result<ExamineRecordReturnVO> queryExamineRecordInfo(@RequestParam("recordId") Integer recordId) {
		return Result.ok(examineRecordService.queryExamineRecordInfo(recordId));
	}


	@PostMapping("/queryExamineLogById")
	@ApiExplain("")
	public Result<ExamineRecordLog> queryExamineLogById(@RequestParam("examineLogId") Integer examineLogId) {
		return Result.ok(examineRecordLogService.getById(examineLogId));
	}

	@PostMapping("/deleteExamineRecord")
	@ApiExplain("")
	public Result<Boolean> deleteExamineRecord(@RequestParam("recordId") Integer recordId) {
		return Result.ok(examineRecordService.deleteExamineRecord(recordId));
	}

	@PostMapping("/updateExamineRecord")
	@ApiExplain("")
	public Result<Boolean> updateExamineRecord(@RequestParam("recordId") Integer recordId, @RequestParam("examineStatus") Integer examineStatus) {
		return Result.ok(examineRecordService.updateExamineRecord(recordId, examineStatus));
	}


	@PostMapping("/deleteExamineRecordAndLog")
	@ApiExplain("")
	public Result<Boolean> deleteExamineRecordAndLog(@RequestParam("label") Integer label) {
		return Result.ok(examineRecordService.deleteExamineRecordAndLog(label));
	}

	@PostMapping("/queryExamineRecordList")
	@ApiOperation("")
	public Result<ExamineRecordVO> queryExamineRecordList(@RequestParam("recordId") Integer recordId, @RequestParam(value = "ownerUserId", required = false) Long ownerUserId) {
		return Result.ok(examineRecordService.queryExamineRecordList(recordId, ownerUserId));
	}


}

