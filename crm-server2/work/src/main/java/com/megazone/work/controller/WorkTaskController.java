package com.megazone.work.controller;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.work.common.log.WorkTaskLog;
import com.megazone.work.entity.BO.*;
import com.megazone.work.entity.PO.WorkTask;
import com.megazone.work.service.IWorkTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-18
 */
@RestController
@RequestMapping("/workTask")
@Api(tags = "")
@Slf4j
@SysLog(subModel = SubModelType.WORK_TASK, logClass = WorkTaskLog.class)
public class WorkTaskController {
	@Autowired
	private IWorkTaskService workTaskService;

	@PostMapping("/myTask")
	@ApiOperation("")
	public Result myTask(@RequestBody WorkTaskNameBO workTaskNameBO) {
		return R.ok(workTaskService.myTask(workTaskNameBO, false));
	}

	@PostMapping("/updateTop")
	@ApiOperation("")
	public Result updateTop(@RequestBody UpdateTaskTopBo updateTaskClassBo) {
		workTaskService.updateTop(updateTaskClassBo);
		return R.ok();
	}

	@PostMapping("/saveWorkTask")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#workTask.name", detail = "'New task created: '+#workTask.name")
	public Result saveWorkTask(@RequestBody WorkTask workTask) {
		workTaskService.saveWorkTask(workTask);
		return R.ok();
	}


	@PostMapping("/updateWorkTask")
	@ApiOperation("")
	public Result<Boolean> updateWorkTask(@RequestBody WorkTask workTask) {
		return R.ok(workTaskService.updateWorkTask(workTask));
	}

	@PostMapping("/setWorkTaskStatus")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result setWorkTaskStatus(@RequestBody WorkTaskStatusBO workTaskStatusBO) {
		workTaskService.setWorkTaskStatus(workTaskStatusBO);
		return R.ok();
	}

	@PostMapping("/setWorkTaskTitle")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result setWorkTaskTitle(@RequestBody WorkTaskNameBO workTaskNameBO) {
		workTaskService.setWorkTaskTitle(workTaskNameBO);
		return R.ok();
	}

	@PostMapping("/setWorkTaskDescription")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result setWorkTaskDescription(@RequestBody WorkTaskDescriptionBO workTaskDescriptionBO) {
		workTaskService.setWorkTaskDescription(workTaskDescriptionBO);
		return R.ok();
	}

	@PostMapping("/setWorkTaskMainUser")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result setWorkTaskMainUser(@RequestBody WorkTaskUserBO workTaskUserBO) {
		workTaskService.setWorkTaskMainUser(workTaskUserBO);
		return R.ok();
	}

	@PostMapping("/setWorkTaskOwnerUser")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result setWorkTaskOwnerUser(@RequestBody WorkTaskOwnerUserBO workTaskOwnerUserBO) {
		workTaskService.setWorkTaskOwnerUser(workTaskOwnerUserBO);
		return R.ok();
	}

	@PostMapping("/setWorkTaskTime")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result setWorkTaskTime(@RequestBody WorkTask workTask) {
		workTaskService.setWorkTaskTime(workTask);
		return R.ok();
	}

	@PostMapping("/setWorkTaskLabel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result setWorkTaskLabel(@RequestBody WorkTaskLabelsBO workTaskLabelsBO) {
		workTaskService.setWorkTaskLabel(workTaskLabelsBO);
		return R.ok();
	}

	@PostMapping("/setWorkTaskPriority")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result setWorkTaskPriority(@RequestBody WorkTaskPriorityBO workTaskPriorityBO) {
		workTaskService.setWorkTaskPriority(workTaskPriorityBO);
		return R.ok();
	}

	@PostMapping("/addWorkChildTask")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE)
	public Result<WorkTask> addWorkChildTask(@RequestBody WorkTask workTask) {
		WorkTask task = workTaskService.addWorkChildTask(workTask);
		return R.ok(task);
	}

	@PostMapping("/updateWorkChildTask")
	@ApiOperation("")
	public Result updateWorkChildTask(@RequestBody WorkTask workTask) {
		workTaskService.updateWorkChildTask(workTask);
		return R.ok();
	}

	@PostMapping("/setWorkChildTaskStatus")
	@ApiOperation("")
	public Result setWorkChildTaskStatus(@RequestBody WorkTaskStatusBO workTaskStatusBO) {
		workTaskService.setWorkChildTaskStatus(workTaskStatusBO);
		return R.ok();
	}

	@PostMapping("/deleteWorkTaskOwnerUser")
	@ApiOperation("")
	public Result deleteWorkTaskOwnerUser(@RequestBody WorkTaskUserBO workTaskUserBO) {
		workTaskService.deleteWorkTaskOwnerUser(workTaskUserBO);
		return R.ok();
	}

	@PostMapping("/deleteWorkTaskLabel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result deleteWorkTaskLabel(@RequestBody WorkTaskLabelBO workTaskLabelBO) {
		workTaskService.deleteWorkTaskLabel(workTaskLabelBO);
		return R.ok();
	}

	@PostMapping("/queryTaskInfo/{taskId}")
	@ApiOperation("")
	public Result queryTaskInfo(@PathVariable Integer taskId) {
		return R.ok(workTaskService.queryTaskInfo(taskId));
	}

	@PostMapping("/queryTrashTaskInfo")
	@ApiOperation("")
	public Result queryTrashTaskInfo(@RequestParam("taskId") Integer taskId) {
		return R.ok(workTaskService.queryTrashTaskInfo(taskId));
	}

	@PostMapping("/deleteWorkTask/{taskId}")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteWorkTask(@PathVariable Integer taskId) {
		workTaskService.deleteWorkTask(taskId);
		return R.ok();
	}

	@PostMapping("/deleteWorkChildTask/{taskId}")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteWorkChildTask(@PathVariable Integer taskId) {
		workTaskService.deleteWorkTask(taskId);
		return R.ok();
	}

	@PostMapping("/archiveByTaskId/{taskId}")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.ARCHIVE)
	public Result archiveByTaskId(@PathVariable Integer taskId) {
		workTaskService.archiveByTaskId(taskId);
		return R.ok();
	}

	@PostMapping("/uploadWorkTaskFile")
	@ApiOperation("")
	public Result uploadWorkTaskFile() {
		return R.ok();
	}

	@PostMapping("/archiveByTaskId")
	@ApiOperation("")
	public Result deleteWorkTaskFile() {
		return R.ok();
	}

	@PostMapping("/queryTrashList")
	@ApiOperation("")
	public Result queryTrashList() {
		return R.ok(workTaskService.queryTrashList());
	}

	@PostMapping("/deleteTask/{taskId}")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteTask(@PathVariable Integer taskId) {
		workTaskService.deleteTask(taskId);
		return R.ok();
	}

	@PostMapping("/restore/{taskId}")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.RESTORE)
	public Result restore(@PathVariable Integer taskId) {
		workTaskService.restore(taskId);
		return R.ok();
	}

	@PostMapping("/queryTaskList")
	@ApiOperation("oa")
	public Result queryTaskList(@RequestBody OaTaskListBO oaTaskListBO) {
		return R.ok(workTaskService.queryTaskList(oaTaskListBO));
	}

	@PostMapping("/workBenchTaskExport")
	@ApiOperation("Export workbench task")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Export Workbench Task", detail = "Export Workbench Task")
	public void workBenchTaskExport(HttpServletResponse response) {
		List<Map<String, Object>> list = workTaskService.workBenchTaskExport();
		ExcelWriter writer = ExcelUtil.getWriter();
		try {
			writer.addHeaderAlias("name", "Task Name");
			writer.addHeaderAlias("description", "Task description");
			writer.addHeaderAlias("mainUserName", "responsible person");
			writer.addHeaderAlias("startTime", "Start Time");
			writer.addHeaderAlias("stopTime", "End Time");
			writer.addHeaderAlias("labelName", "label");
			writer.addHeaderAlias("ownerUserName", "Participant");
			writer.addHeaderAlias("priority", "priority");
			writer.addHeaderAlias("createUserName", "Creator");
			writer.addHeaderAlias("createTime", "create time");
			writer.addHeaderAlias("isTop", "The task list");
			writer.addHeaderAlias("relateCrmWork", "Associated Business");
			writer.merge(11, "Workbench task information");
			writer.setOnlyAlias(true);
			writer.write(list, true);
			writer.setRowHeight(0, 20);
			writer.setRowHeight(1, 20);
			for (int i = 0; i < 12; i++) {
				writer.setColumnWidth(i, 20);
			}
			Cell cell = writer.getCell(0, 0);
			CellStyle cellStyle = cell.getCellStyle();
			cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = writer.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) 16);
			cellStyle.setFont(font);
			cell.setCellStyle(cellStyle);
			// custom title alias
			//response is the HttpServletResponse object
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			//test.xls is the file name of the pop-up download dialog, which cannot be Chinese, please encode the Chinese by yourself
			response.setHeader("Content-Disposition", "attachment;filename=myTask.xls");
			ServletOutputStream out = response.getOutputStream();
			writer.flush(out);
		} catch (Exception e) {
			log.error("Error exporting workbench task information: ", e);
		} finally {
			// close the writer, free memory
			writer.close();
		}
	}

	@PostMapping("/updateTaskJob")
	public Result updateTaskJob() {
		workTaskService.lambdaUpdate()
				.set(WorkTask::getStatus, 2)
				.eq(WorkTask::getStatus, 1).apply("stop_time < now()").update();
		return Result.ok();
	}

}
