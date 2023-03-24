package com.megazone.oa.controller;


import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.oa.entity.BO.SetExamineCategoryBO;
import com.megazone.oa.entity.BO.UpdateCategoryStatus;
import com.megazone.oa.entity.PO.OaExamineCategory;
import com.megazone.oa.entity.PO.OaExamineSort;
import com.megazone.oa.entity.VO.OaExamineCategoryVO;
import com.megazone.oa.service.IOaExamineCategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oaExamineCategory")
public class OaExamineCategoryController {

	@Autowired
	private IOaExamineCategoryService examineCategoryService;


	@ApiOperation("Set approval type")
	@PostMapping("/setExamineCategory")
	public Result<Map<String, Integer>> setExamineCategory(@RequestBody SetExamineCategoryBO setExamineCategoryBO) {
		Map<String, Integer> map = examineCategoryService.setExamineCategory(setExamineCategoryBO);
		return Result.ok(map);
	}

	@ApiOperation("Query the list of approval types")
	@PostMapping("/queryExamineCategoryList")
	public Result<BasePage<OaExamineCategoryVO>> queryExamineCategoryList(PageEntity pageEntity) {
		BasePage<OaExamineCategoryVO> page = examineCategoryService.queryExamineCategoryList(pageEntity);
		return Result.ok(page);
	}


	@ApiOperation("Query the list of approval types")
	@PostMapping("/queryAllExamineCategoryList")
	public Result<List<OaExamineCategory>> queryAllExamineCategoryList() {
		List<OaExamineCategory> examineCategoryList = examineCategoryService.queryAllExamineCategoryList();
		return Result.ok(examineCategoryList);
	}

	@ApiOperation("Custom approval type sorting")
	@PostMapping("/saveOrUpdateOaExamineSort")
	public Result saveOrUpdateOaExamineSort(@RequestBody List<OaExamineSort> oaExamineSortList) {
		examineCategoryService.saveOrUpdateOaExamineSort(oaExamineSortList);
		return Result.ok();
	}


	@ApiOperation("Delete approval type")
	@PostMapping("/deleteExamineCategory")
	public Result deleteExamineCategory(@RequestParam("id") Integer id) {
		examineCategoryService.deleteExamineCategory(id);
		return Result.ok();
	}

	/**
	 * enable/disable
	 */
	@ApiOperation("Delete approval type")
	@PostMapping("/updateStatus")
	public Result updateStatus(@RequestBody UpdateCategoryStatus updateCategoryStatus) {
		examineCategoryService.updateStatus(updateCategoryStatus);
		return Result.ok();
	}


}
