package com.megazone.oa.controller;

import com.megazone.core.common.Result;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.oa.constart.entity.BO.*;
import com.megazone.oa.constart.entity.PO.WorkTask;
import com.megazone.oa.constart.entity.VO.OaTaskListVO;
import com.megazone.oa.service.IOaTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oaTask")
@Api(tags = "oa task")
public class OaTaskController {

	@Autowired
	private IOaTaskService oaTaskService;

	@PostMapping("/saveWorkTask")
	public Result saveWorkTask(@RequestBody WorkTask workTask) {
		return oaTaskService.saveWorkTask(workTask);
	}

	@PostMapping("/setWorkTaskStatus")
	@ApiOperation("Set project task status")
	public Result setWorkTaskStatus(@RequestBody WorkTaskStatusBO workTaskStatusBO) {
		return oaTaskService.setWorkTaskStatus(workTaskStatusBO);
	}

	@PostMapping("/setWorkTaskTitle")
	@ApiOperation("Set project task title")
	public Result setWorkTaskTitle(@RequestBody WorkTaskNameBO workTaskNameBO) {
		return oaTaskService.setWorkTaskTitle(workTaskNameBO);
	}

	@PostMapping("/setWorkTaskDescription")
	@ApiOperation("Set project task description")
	public Result setWorkTaskDescription(@RequestBody WorkTaskDescriptionBO workTaskDescriptionBO) {
		return oaTaskService.setWorkTaskDescription(workTaskDescriptionBO);

	}

	@PostMapping("/setWorkTaskMainUser")
	@ApiOperation("Set project task owner")
	public Result setWorkTaskMainUser(@RequestBody WorkTaskUserBO workTaskUserBO) {
		return oaTaskService.setWorkTaskMainUser(workTaskUserBO);
	}

	@PostMapping("/setWorkTaskOwnerUser")
	@ApiOperation("New project task")
	public Result setWorkTaskOwnerUser(@RequestBody WorkTaskOwnerUserBO workTaskOwnerUserBO) {
		return oaTaskService.setWorkTaskOwnerUser(workTaskOwnerUserBO);
	}

	@PostMapping("/setWorkTaskTime")
	@ApiOperation("Set the start and end time of the project task")
	public Result setWorkTaskTime(@RequestBody WorkTask workTask) {
		return oaTaskService.setWorkTaskTime(workTask);
	}

	@PostMapping("/setWorkTaskLabel")
	@ApiOperation("Set the project task label")
	public Result setWorkTaskLabel(@RequestBody WorkTaskLabelsBO workTaskLabelsBO) {
		return oaTaskService.setWorkTaskLabel(workTaskLabelsBO);
	}

	@PostMapping("/setWorkTaskPriority")
	@ApiOperation("Set project task priority")
	public Result setWorkTaskPriority(@RequestBody WorkTaskPriorityBO workTaskPriorityBO) {
		return oaTaskService.setWorkTaskPriority(workTaskPriorityBO);
	}

	@PostMapping("/addWorkChildTask")
	@ApiOperation("New project subtask")
	public Result addWorkChildTask(@RequestBody WorkTask workTask) {
		return oaTaskService.addWorkChildTask(workTask);
	}

	@PostMapping("/updateWorkChildTask")
	@ApiOperation("Edit project subtask")
	public Result updateWorkChildTask(@RequestBody WorkTask workTask) {
		return oaTaskService.updateWorkChildTask(workTask);
	}

	@PostMapping("/setWorkChildTaskStatus")
	@ApiOperation("Set the status of project subtasks")
	public Result setWorkChildTaskStatus(@RequestBody WorkTaskStatusBO workTaskStatusBO) {
		return oaTaskService.setWorkChildTaskStatus(workTaskStatusBO);
	}

	@PostMapping("/deleteWorkTaskOwnerUser")
	@ApiOperation("Remove project subtask leader")
	public Result deleteWorkTaskOwnerUser(@RequestBody WorkTaskUserBO workTaskUserBO) {
		return oaTaskService.deleteWorkTaskOwnerUser(workTaskUserBO);
	}

	@PostMapping("/deleteWorkTaskLabel")
	@ApiOperation("Delete project task label")
	public Result deleteWorkTaskLabel(@RequestBody WorkTaskLabelBO workTaskLabelBO) {
		return oaTaskService.deleteWorkTaskLabel(workTaskLabelBO);
	}

	@PostMapping("/deleteWorkChildTask/{taskId}")
	@ApiOperation("Delete subtask")
	public Result deleteWorkChildTask(@PathVariable Integer taskId) {
		return oaTaskService.deleteWorkChildTask(taskId);
	}

	@PostMapping("/queryTaskList")
	@ApiOperation("View oa task list")
	public Result<OaTaskListVO> queryTaskList(@RequestBody OaTaskListBO oaTaskListBO) {
		Result<OaTaskListVO> listVO = oaTaskService.queryTaskList(oaTaskListBO);
		return listVO;
	}

	@PostMapping("/oaTaskExport")
	@ApiOperation("task export")
	public void oaTaskExport(@RequestBody OaTaskListBO oaTaskListBO, HttpServletResponse response) {
		oaTaskListBO.setIsExport(true);
		OaTaskListVO data = oaTaskService.queryTaskList(oaTaskListBO).getData();
		List<Map<String, Object>> list = data.getExportList();
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("name", "task name"));
		dataList.add(ExcelParseUtil.toEntity("description", "task description"));
		dataList.add(ExcelParseUtil.toEntity("mainUserName", "Person in charge"));
		dataList.add(ExcelParseUtil.toEntity("startTime", "Start Time"));
		dataList.add(ExcelParseUtil.toEntity("stopTime", "End Time"));
		dataList.add(ExcelParseUtil.toEntity("labelName", "label"));
		dataList.add(ExcelParseUtil.toEntity("ownerUserName", "Participant"));
		dataList.add(ExcelParseUtil.toEntity("priority", "priority"));
		dataList.add(ExcelParseUtil.toEntity("createUserName", "Creator"));
		dataList.add(ExcelParseUtil.toEntity("createTime", "create time"));
		dataList.add(ExcelParseUtil.toEntity("relateCrmWork", "Associated Business"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "task";
			}
		}, dataList);
	}

}
