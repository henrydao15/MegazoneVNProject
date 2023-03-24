package com.megazone.work.controller;


import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.work.service.IWorkCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/workCollect")
@Api(tags = "")
public class WorkCollectController {
	@Autowired
	private IWorkCollectService workCollectService;

	@PostMapping("/collect/{workId}")
	@ApiOperation("")
	public Result collect(@PathVariable @NotNull Integer workId) {
		workCollectService.collect(workId);
		return R.ok();
	}
}

