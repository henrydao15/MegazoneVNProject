package com.megazone.oa.controller;


import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.oa.entity.BO.ExamineFieldBO;
import com.megazone.oa.service.IOaExamineFieldService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oaExamineField")
public class OaExamineFieldController {

	@Autowired
	private IOaExamineFieldService examineFieldService;

	@ApiOperation("Query to add or edit fields")
	@PostMapping("/queryField/{categoryId}")
	public Result<List> queryField(@PathVariable Integer categoryId,
								   @RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(examineFieldService.queryField(categoryId));
		}
		return R.ok(examineFieldService.queryFormPositionField(categoryId));
	}

	@ApiOperation("Save custom fields")
	@PostMapping("/saveField")
	public Result saveField(@RequestBody ExamineFieldBO examineFieldBO) {
		examineFieldService.saveField(examineFieldBO);
		return Result.ok();
	}

	@ApiExplain("Save default fields")
	@PostMapping("/saveDefaultField")
	public Result saveDefaultField(@RequestParam("categoryId") Long categoryId) {
		examineFieldService.saveDefaultField(categoryId);
		return Result.ok();
	}

	@ApiOperation("Modify custom fields")
	@PostMapping("/updateFieldCategoryId")
	public Result<Boolean> updateFieldCategoryId(@RequestParam("newCategoryId") Long newCategoryId, @RequestParam("oldCategoryId") Long oldCategoryId) {
		return Result.ok(examineFieldService.updateFieldCategoryId(newCategoryId, oldCategoryId));
	}


}
