package com.megazone.examine.controller;

import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.oa.entity.ExamineVO;
import com.megazone.examine.entity.BO.ExaminePageBO;
import com.megazone.examine.entity.VO.ExamineRecordInfoVO;
import com.megazone.examine.service.IExamineService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @date 2020/12/24
 */
@RestController
@RequestMapping("/examineWaiting")
public class MyExamineController {
	@Autowired
	private IExamineService examineService;

	@PostMapping("/queryOaExamineList")
	@ApiOperation("OA")
	public Result<BasePage<ExamineVO>> queryOaExamineList(@RequestBody ExaminePageBO examinePageBo) {
		BasePage<ExamineVO> voBasePage = examineService.queryOaExamineList(examinePageBo);
		return Result.ok(voBasePage);
	}


	@PostMapping("/queryCrmExamineList")
	@ApiOperation("CRM")
	public Result<BasePage<ExamineRecordInfoVO>> queryCrmExamineList(@RequestBody ExaminePageBO examinePageBo) {
		BasePage<ExamineRecordInfoVO> voBasePage = examineService.queryCrmExamineList(examinePageBo);
		return Result.ok(voBasePage);
	}


	@PostMapping("/queryOaExamineIdList")
	@ApiExplain("OA")
	public Result<List<Integer>> queryOaExamineIdList(@RequestParam(value = "status", required = false) Integer status, @RequestParam("categoryId") Integer categoryId) {
		return Result.ok(examineService.queryOaExamineIdList(status, categoryId));
	}

	@PostMapping("/queryCrmExamineIdList")
	@ApiExplain("CRM")
	public Result<List<Integer>> queryCrmExamineIdList(@RequestParam("label") Integer label, @RequestParam(value = "status", required = false) Integer status) {
		return Result.ok(examineService.queryCrmExamineIdList(label, status));
	}

}
