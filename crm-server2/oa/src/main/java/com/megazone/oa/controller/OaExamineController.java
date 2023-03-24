package com.megazone.oa.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.ExamineField;
import com.megazone.core.feign.examine.entity.ExamineConditionDataBO;
import com.megazone.core.feign.examine.entity.ExamineInfoVo;
import com.megazone.core.feign.examine.service.ExamineService;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.oa.entity.BO.AuditExamineBO;
import com.megazone.oa.entity.BO.ExamineExportBO;
import com.megazone.oa.entity.BO.ExaminePageBO;
import com.megazone.oa.entity.BO.GetExamineFieldBO;
import com.megazone.oa.entity.PO.OaExamineCategory;
import com.megazone.oa.entity.PO.OaExamineField;
import com.megazone.oa.entity.VO.ExamineVO;
import com.megazone.oa.service.IOaExamineFieldService;
import com.megazone.oa.service.IOaExamineService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/oaExamine")
@Slf4j
public class OaExamineController {

	@Autowired
	private IOaExamineService oaExamineService;

	@Autowired
	private IOaExamineFieldService examineFieldService;

	@Autowired
	private ExamineService examineService;

	@ApiOperation("Approval initiated by me")
	@PostMapping("/myInitiate")
	public Result<BasePage<ExamineVO>> myInitiate(@RequestBody ExaminePageBO examinePageBO) {
		BasePage<ExamineVO> page = oaExamineService.myInitiate(examinePageBO);
		return Result.ok(page);
	}


	@ApiOperation("I approved")
	@PostMapping("/myOaExamine")
	public Result<BasePage<ExamineVO>> myOaExamine(@RequestBody ExaminePageBO examinePageBO) {
		BasePage<ExamineVO> page = oaExamineService.myOaExamine(examinePageBO);
		return Result.ok(page);
	}

	@ApiExplain("Get the specified approval information")
	@PostMapping("/getOaExamineById")
	public Result<ExamineVO> getOaExamineById(@RequestParam("oaExamineId") Integer oaExamineId) {
		ExamineVO examineVO = oaExamineService.getOaExamineById(oaExamineId);
		return Result.ok(examineVO);
	}


	@ApiOperation("Query details or edit fields")
	@PostMapping("/getField")
	public Result<List> getField(@RequestBody GetExamineFieldBO getExamineFieldBO) {
		if (StrUtil.isNotEmpty(getExamineFieldBO.getType())) {
			return Result.ok(oaExamineService.getField(getExamineFieldBO));
		}
		return Result.ok(oaExamineService.getFormPositionField(getExamineFieldBO));
	}


	@PostMapping("/setOaExamine")
	@ApiOperation("Create Approval")
	public Result setOaExamine(@RequestBody JSONObject jsonObject) {
		oaExamineService.setOaExamine(jsonObject);
		return Result.ok();
	}


	@PostMapping("/auditExamine")
	@ApiOperation("Approval")
	public Result auditExamine(@RequestBody AuditExamineBO auditExamineBO) {
		oaExamineService.oaExamine(auditExamineBO);
		return Result.ok();
	}


	@PostMapping("/queryOaExamineInfo/{examineId}")
	@ApiOperation("Query approval details")
	public Result<ExamineVO> queryOaExamineInfo(@PathVariable String examineId) {
		ExamineVO examineVO = oaExamineService.queryOaExamineInfo(examineId);
		return Result.ok(examineVO);
	}


	@PostMapping("/queryExamineRecordList")
	@ApiOperation("Query Approval Step")
	public Result<JsonNode> queryExamineRecordList(@RequestParam("recordId") String recordId) {
		JSONObject jsonObject = oaExamineService.queryExamineRecordList(recordId);
		return Result.ok(jsonObject.node);
	}


	@PostMapping("/queryExamineLogList")
	@ApiOperation("Query approval history")
	public Result<List<JSONObject>> queryExamineLogList(@RequestParam("recordId") Integer recordId) {
		List<JSONObject> jsonObjects = oaExamineService.queryExamineLogList(recordId);
		return Result.ok(jsonObjects);
	}


	@PostMapping("/deleteOaExamine")
	@ApiOperation("Delete Approval")
	public Result deleteOaExamine(@RequestParam("examineId") Integer examineId) {
		oaExamineService.deleteOaExamine(examineId);
		return Result.ok();
	}

	/**
	 * @author Query approval steps
	 */
	@PostMapping("/queryExaminStep")
	@ApiOperation("Query Approval Step")
	public Result<OaExamineCategory> queryExaminStep(@RequestParam("categoryId") String categoryId) {
		OaExamineCategory examineCategory = oaExamineService.queryExaminStep(categoryId);
		return Result.ok(examineCategory);
	}


	/**
	 * export
	 */
	@PostMapping("/export")
	@ApiOperation("Query Approval Step")
	public void export(@RequestBody ExamineExportBO examineExportBO, HttpServletResponse response) {
		Integer categoryId = examineExportBO.getCategoryId();
		ExamineInfoVo examineInfoVo = examineService.queryExamineById(Long.valueOf(categoryId)).getData();
		examineExportBO.setCategoryId(examineInfoVo.getExamineInitId().intValue());
		Integer type = examineInfoVo.getOaType();
		List<OaExamineField> fieldList = new ArrayList<>();
		if (type == 0) {
			fieldList = examineFieldService.lambdaQuery().eq(OaExamineField::getExamineCategoryId, categoryId).list();
		}
		List<Map<String, Object>> list = oaExamineService.export(examineExportBO, examineInfoVo, fieldList);

		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("category", "Approval Type"));
		dataList.add(ExcelParseUtil.toEntity("createTime", "created date"));
		dataList.add(ExcelParseUtil.toEntity("createUserName", "Creator"));
		dataList.add(ExcelParseUtil.toEntity("examineStatus", "Status"));
		dataList.add(ExcelParseUtil.toEntity("examineUserName", "Current approver"));
		switch (type) {
			case 1:
				dataList.add(ExcelParseUtil.toEntity("content", "Approval content"));
				dataList.add(ExcelParseUtil.toEntity("remark", "Remark"));
				break;
			case 2:
				dataList.add(ExcelParseUtil.toEntity("content", "Approval content"));
				dataList.add(ExcelParseUtil.toEntity("startTime", "Start Time"));
				dataList.add(ExcelParseUtil.toEntity("endTime", "End Time"));
				dataList.add(ExcelParseUtil.toEntity("duration", "Duration"));
				dataList.add(ExcelParseUtil.toEntity("remark", "Remark"));
				break;
			case 3:
				dataList.add(ExcelParseUtil.toEntity("content", "Reasons for business trip"));
				dataList.add(ExcelParseUtil.toEntity("remark", "Remark"));
				dataList.add(ExcelParseUtil.toEntity("duration", "Total days on business trip"));
				dataList.add(ExcelParseUtil.toEntity("vehicle", "vehicle"));
				dataList.add(ExcelParseUtil.toEntity("trip", "one way round trip"));
				dataList.add(ExcelParseUtil.toEntity("startAddress", "Departure City"));
				dataList.add(ExcelParseUtil.toEntity("endAddress", "Destination City"));
				dataList.add(ExcelParseUtil.toEntity("startTime", "Start Time"));
				dataList.add(ExcelParseUtil.toEntity("endTime", "End Time"));
				dataList.add(ExcelParseUtil.toEntity("travelDuration", "Duration"));
				dataList.add(ExcelParseUtil.toEntity("description", "Travel Notes"));
				break;
			case 4:
				dataList.add(ExcelParseUtil.toEntity("content", "Reason for overtime"));
				dataList.add(ExcelParseUtil.toEntity("startTime", "Start Time"));
				dataList.add(ExcelParseUtil.toEntity("endTime", "End Time"));
				dataList.add(ExcelParseUtil.toEntity("duration", "Total overtime days"));
				dataList.add(ExcelParseUtil.toEntity("remark", "Remark"));
				break;
			case 5:
				dataList.add(ExcelParseUtil.toEntity("content", "Travel content"));
				dataList.add(ExcelParseUtil.toEntity("money", "Total Reimbursement Amount"));
				dataList.add(ExcelParseUtil.toEntity("remark", "Remark"));
				dataList.add(ExcelParseUtil.toEntity("startAddress", "Departure City"));
				dataList.add(ExcelParseUtil.toEntity("endAddress", "Destination City"));
				dataList.add(ExcelParseUtil.toEntity("startTime", "Start Time"));
				dataList.add(ExcelParseUtil.toEntity("endTme", "End Time"));
				dataList.add(ExcelParseUtil.toEntity("traffic", "traffic"));
				dataList.add(ExcelParseUtil.toEntity("stay", "stay"));
				dataList.add(ExcelParseUtil.toEntity("diet", "Diet"));
				dataList.add(ExcelParseUtil.toEntity("other", "other charges"));
				dataList.add(ExcelParseUtil.toEntity("travelMoney", "Total"));
				dataList.add(ExcelParseUtil.toEntity("description", "Expense detail description"));
				break;
			case 6:
				dataList.add(ExcelParseUtil.toEntity("content", "Approval content"));
				dataList.add(ExcelParseUtil.toEntity("money", "Loan Amount"));
				dataList.add(ExcelParseUtil.toEntity("remark", "Remark"));
				break;
			case 0:
				fieldList.forEach(field -> dataList.add(ExcelParseUtil.toEntity(field.getFieldName(), field.getName())));
				break;
			default:
				break;
		}
		dataList.add(ExcelParseUtil.toEntity("relateCrmWork", "Associated Business"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "log";
			}
		}, dataList);
	}

	@PostMapping("/transfer")
	@ApiOperation("Transition Approval")
	public Result<List<ExamineVO>> transfer(@RequestBody List<ExamineVO> recordList) {
		List<ExamineVO> transfer = oaExamineService.transfer(recordList);
		return Result.ok(transfer);
	}


	@ApiOperation("query field")
	@PostMapping("/queryExamineField")
	public Result<List<ExamineField>> queryExamineField(@RequestParam("categoryId") Integer categoryId) {
		List<OaExamineField> oaExamineFields = examineFieldService.queryField(categoryId);
		List<ExamineField> examineFields = new ArrayList<>();
		oaExamineFields.forEach(oaExamineField -> {
			boolean isNeed = Objects.equals(oaExamineField.getIsNull(), 1) && ListUtil.toList(3, 5, 6, 9).contains(oaExamineField.getType());
			if (isNeed) {
				examineFields.add(BeanUtil.copyProperties(oaExamineField, ExamineField.class));
			}
		});
		return Result.ok(examineFields);
	}

	@ApiOperation("Query condition field")
	@PostMapping("/queryConditionData")
	public Result<Map<String, Object>> getDataMapForNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO) {
		return Result.ok(oaExamineService.getDataMapForNewExamine(examineConditionDataBO));
	}

	@PostMapping("/updateCheckStatusByNewExamine")
	public Result<Boolean> updateCheckStatusByNewExamine(@RequestBody ExamineConditionDataBO examineConditionDataBO) {
		return Result.ok(oaExamineService.updateCheckStatusByNewExamine(examineConditionDataBO));
	}
}

