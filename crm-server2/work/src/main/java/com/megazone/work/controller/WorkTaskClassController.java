package com.megazone.work.controller;


import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.work.entity.PO.WorkTaskClass;
import com.megazone.work.service.IWorkTaskClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
@RestController
@RequestMapping("/workTaskClass")
@Api(tags = "")
public class WorkTaskClassController {
	@Autowired
	private IWorkTaskClassService workTaskClassService;

	@PostMapping("/queryClassNameByWorkId/{workId}")
	@ApiOperation("id")
	public Result queryClassNameByWorkId(@PathVariable @NotNull Integer workId) {
		return R.ok(workTaskClassService.queryClassNameByWorkId(workId));
	}

	@PostMapping("/saveWorkTaskClass")
	@ApiOperation("")
	public Result saveWorkTaskClass(@RequestBody WorkTaskClass workTaskClass) {
		workTaskClassService.saveWorkTaskClass(workTaskClass);
		return R.ok();
	}

	@PostMapping("/updateWorkTaskClass")
	@ApiOperation("")
	public Result updateWorkTaskClass(@RequestBody WorkTaskClass workTaskClass) {
		workTaskClassService.updateWorkTaskClass(workTaskClass);
		return R.ok();
	}
}

