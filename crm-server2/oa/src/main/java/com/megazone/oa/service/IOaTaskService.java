package com.megazone.oa.service;

import com.megazone.core.common.Result;
import com.megazone.oa.constart.entity.BO.*;
import com.megazone.oa.constart.entity.PO.WorkTask;
import com.megazone.oa.constart.entity.VO.OaTaskListVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "work")
public interface IOaTaskService {

	@PostMapping("/workTask/saveWorkTask")
	public Result saveWorkTask(@RequestBody WorkTask workTask);

	@PostMapping("/workTask/setWorkTaskStatus")
	public Result setWorkTaskStatus(@RequestBody WorkTaskStatusBO workTaskStatusBO);

	@PostMapping("/workTask/setWorkTaskTitle")
	public Result setWorkTaskTitle(@RequestBody WorkTaskNameBO workTaskNameBO);

	@PostMapping("/workTask/setWorkTaskDescription")
	public Result setWorkTaskDescription(@RequestBody WorkTaskDescriptionBO workTaskDescriptionBO);

	@PostMapping("/workTask/setWorkTaskMainUser")
	public Result setWorkTaskMainUser(@RequestBody WorkTaskUserBO workTaskUserBO);

	@PostMapping("/workTask/setWorkTaskOwnerUser")
	public Result setWorkTaskOwnerUser(@RequestBody WorkTaskOwnerUserBO workTaskOwnerUserBO);

	@PostMapping("/workTask/setWorkTaskTime")
	public Result setWorkTaskTime(@RequestBody WorkTask workTask);

	@PostMapping("/workTask/setWorkTaskLabel")
	public Result setWorkTaskLabel(@RequestBody WorkTaskLabelsBO workTaskLabelsBO);

	@PostMapping("/workTask/setWorkTaskPriority")
	public Result setWorkTaskPriority(@RequestBody WorkTaskPriorityBO workTaskPriorityBO);

	@PostMapping("/workTask/addWorkChildTask")
	public Result addWorkChildTask(@RequestBody WorkTask workTask);

	@PostMapping("/workTask/updateWorkChildTask")
	public Result updateWorkChildTask(@RequestBody WorkTask workTask);

	@PostMapping("/workTask/setWorkChildTaskStatus")
	public Result setWorkChildTaskStatus(@RequestBody WorkTaskStatusBO workTaskStatusBO);

	@PostMapping("/workTask/deleteWorkTaskOwnerUser")
	public Result deleteWorkTaskOwnerUser(@RequestBody WorkTaskUserBO workTaskUserBO);

	@PostMapping("/workTask/deleteWorkTaskLabel")
	public Result deleteWorkTaskLabel(@RequestBody WorkTaskLabelBO workTaskLabelBO);

	@PostMapping("/workTask/deleteWorkChildTask/{taskId}")
	public Result deleteWorkChildTask(@PathVariable Integer taskId);

	@PostMapping("/workTask/queryTaskList")
	public Result<OaTaskListVO> queryTaskList(@RequestBody OaTaskListBO oaTaskListBO);
}
