package com.megazone.work.controller;


import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.work.entity.PO.WorkTaskRelation;
import com.megazone.work.service.IWorkTaskRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-18
 */
@RestController
@RequestMapping("/workTaskRelation")
@Api(tags = "")
public class WorkTaskRelationController {
	@Autowired
	private IWorkTaskRelationService workTaskRelationService;

	@PostMapping("/saveWorkTaskRelation")
	@ApiOperation("")
	public Result saveWorkTaskRelation(@RequestBody WorkTaskRelation workTaskRelation) {
		workTaskRelationService.saveWorkTaskRelation(workTaskRelation);
		return R.ok();
	}
}

