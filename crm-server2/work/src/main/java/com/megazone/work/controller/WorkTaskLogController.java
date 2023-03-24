package com.megazone.work.controller;


import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.work.service.IWorkTaskLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
@RestController
@RequestMapping("/workTaskLog")
@Api(tags = "")
public class WorkTaskLogController {
	@Autowired
	private IWorkTaskLogService workTaskLogService;

	@PostMapping("/queryTaskLog/{taskId}")
	@ApiOperation("")
	public Result queryTaskLog(@PathVariable Integer taskId) {
		return R.ok(workTaskLogService.queryTaskLog(taskId));
	}
}

