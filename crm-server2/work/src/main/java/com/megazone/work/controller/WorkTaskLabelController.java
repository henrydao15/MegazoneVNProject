package com.megazone.work.controller;


import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.work.common.log.WorkTaskLabelLog;
import com.megazone.work.entity.PO.WorkTaskLabel;
import com.megazone.work.service.IWorkTaskLabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
@RestController
@RequestMapping("/workTaskLabel")
@Api(tags = "")
@SysLog(subModel = SubModelType.WORK_TASK, logClass = WorkTaskLabelLog.class)
public class WorkTaskLabelController {
	@Autowired
	private IWorkTaskLabelService workTaskLabelService;

	@PostMapping("/saveLabel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#workTaskLabel.name", detail = "'Created new tasks: '+#workTaskLabel.name")
	public Result saveLabel(@RequestBody WorkTaskLabel workTaskLabel) {
		workTaskLabelService.saveLabel(workTaskLabel);
		return R.ok();
	}

	@PostMapping("/setLabel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE, object = "#workTaskLabel.name", detail = "'Edited tasks: '+#workTaskLabel.name")
	public Result setLabel(@RequestBody WorkTaskLabel workTaskLabel) {
		workTaskLabelService.setLabel(workTaskLabel);
		return R.ok();
	}

	@PostMapping("/deleteLabel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteLabel(@RequestParam("labelId") Integer labelId) {
		workTaskLabelService.deleteLabel(labelId);
		return R.ok();
	}

	@PostMapping("/getLabelList")
	@ApiOperation("")
	public Result getLabelList() {
		return R.ok(workTaskLabelService.getLabelList());
	}

	@PostMapping("/updateOrder")
	@ApiOperation("")
	public Result updateOrder(@RequestBody List<Integer> labelIdList) {
		workTaskLabelService.updateOrder(labelIdList);
		return R.ok();
	}

	@PostMapping("/queryById/{labelId}")
	@ApiOperation("")
	public Result queryById(@PathVariable Integer labelId) {
		return R.ok(workTaskLabelService.queryById(labelId));
	}

	@PostMapping("/getTaskList/{labelId}")
	@ApiOperation("")
	public Result getTaskList(@PathVariable Integer labelId) {
		return R.ok(workTaskLabelService.getTaskList(labelId));
	}
}

