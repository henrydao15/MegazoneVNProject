package com.megazone.examine.controller;


import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.ExamineField;
import com.megazone.examine.entity.BO.ExaminePageBO;
import com.megazone.examine.entity.BO.ExaminePreviewBO;
import com.megazone.examine.entity.BO.ExamineSaveBO;
import com.megazone.examine.entity.PO.Examine;
import com.megazone.examine.entity.VO.ExamineFlowConditionDataVO;
import com.megazone.examine.entity.VO.ExamineFlowVO;
import com.megazone.examine.entity.VO.ExaminePreviewVO;
import com.megazone.examine.entity.VO.ExamineVO;
import com.megazone.examine.service.IExamineService;
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
 * @since 2020-11-13
 */
@RestController
@RequestMapping("/examines")
public class ExamineController {

	@Autowired
	private IExamineService examineService;

	@PostMapping("/queryField")
	@ApiOperation("")
	public Result<List<ExamineField>> queryField(@RequestParam("label") Integer label, @RequestParam(value = "categoryId", required = false) Integer categoryId) {
		List<ExamineField> fieldList = examineService.queryField(label, categoryId);
		return Result.ok(fieldList);
	}

	@PostMapping("/queryExamineById")
	@ApiOperation("")
	public Result<Examine> queryExamineById(@RequestParam("examineId") Long examineId) {
		return Result.ok(examineService.getById(examineId));
	}


	@PostMapping("/queryList")
	@ApiOperation("")
	public Result<BasePage<ExamineVO>> queryList(@RequestBody ExaminePageBO examinePageBo) {
		examinePageBo.setIsPart(false);
		BasePage<ExamineVO> voBasePage = examineService.queryList(examinePageBo);
		return Result.ok(voBasePage);
	}

	@PostMapping("/queryPartList")
	@ApiOperation("")
	public Result<BasePage<ExamineVO>> queryPartList(@RequestBody ExaminePageBO examinePageBo) {
		examinePageBo.setIsPart(true);
		BasePage<ExamineVO> voBasePage = examineService.queryList(examinePageBo);
		return Result.ok(voBasePage);
	}


	@PostMapping("/queryNormalExamine")
	@ApiExplain("")
	public Result<List<Examine>> queryNormalExamine(@RequestParam("label") Integer label) {
		List<Examine> examineList = examineService.lambdaQuery().eq(Examine::getLabel, label).eq(Examine::getStatus, 1).list();
		return Result.ok(examineList);
	}

	@PostMapping("/updateStatus")
	@ApiOperation("")
	public Result updateStatus(@RequestParam("status") Integer status, @RequestParam("examineId") Long examineId) {
		examineService.updateStatus(examineId, status, true);
		return Result.ok();
	}

	@PostMapping("/addExamine")
	@ApiOperation("")
	public Result<Examine> addExamine(@RequestBody ExamineSaveBO examineSaveBO) {
		return Result.ok(examineService.addExamine(examineSaveBO));
	}

	@PostMapping("/queryExamineFlow")
	@ApiOperation("")
	public Result<List<ExamineFlowVO>> queryExamineFlow(@RequestParam("examineId") Long examineId) {
		List<ExamineFlowVO> examineFlowVOList = examineService.queryExamineFlow(examineId);
		return Result.ok(examineFlowVOList);
	}


	@PostMapping("/previewFiledName")
	@ApiOperation("")
	public Result<List<ExamineFlowConditionDataVO>> previewFiledName(@RequestBody ExaminePreviewBO examinePreviewBO) {
		List<ExamineFlowConditionDataVO> filedNameList = examineService.previewFiledName(examinePreviewBO.getLabel(), examinePreviewBO.getRecordId(), examinePreviewBO.getExamineId());
		return Result.ok(filedNameList);
	}


	@PostMapping("/previewExamineFlow")
	@ApiOperation("")
	public Result<ExaminePreviewVO> previewExamineFlow(@RequestBody ExaminePreviewBO examinePreviewBO) {
		ExaminePreviewVO examineFlowVO = examineService.previewExamineFlow(examinePreviewBO);
		return Result.ok(examineFlowVO);
	}
}

