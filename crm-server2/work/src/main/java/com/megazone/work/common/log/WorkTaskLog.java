package com.megazone.work.common.log;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.work.entity.BO.*;
import com.megazone.work.entity.PO.WorkTask;
import com.megazone.work.entity.PO.WorkTaskLabel;
import com.megazone.work.service.IWorkTaskLabelService;
import com.megazone.work.service.IWorkTaskService;

import java.util.ArrayList;
import java.util.List;

public class WorkTaskLog {

	private IWorkTaskService workTaskService = ApplicationContextHolder.getBean(IWorkTaskService.class);

	private IWorkTaskLabelService workTaskLabelService = ApplicationContextHolder.getBean(IWorkTaskLabelService.class);

	public Content setWorkTaskStatus(WorkTaskStatusBO workTaskStatusBO) {
		WorkTask workTask = workTaskService.getById(workTaskStatusBO.getTaskId());
		String statusName = "";
		if (workTaskStatusBO.getStatus().equals(1)) {
			statusName = "Incomplete";
		} else if (workTaskStatusBO.getStatus().equals(5)) {
			statusName = "Done";
		}
		return new Content(workTask.getName(), "Modify the status to: " + statusName);
	}

	public Content setWorkTaskTitle(WorkTaskNameBO workTaskNameBO) {
		WorkTask workTask = workTaskService.getById(workTaskNameBO.getTaskId());
		return new Content(workTask.getName(), "Modify task title:" + workTaskNameBO.getName());
	}

	public Content setWorkTaskDescription(WorkTaskDescriptionBO workTaskDescriptionBO) {
		WorkTask workTask = workTaskService.getById(workTaskDescriptionBO.getTaskId());
		return new Content(workTask.getName(), "Modify task description:" + workTaskDescriptionBO.getDescription());
	}

	public Content setWorkTaskMainUser(WorkTaskUserBO workTaskUserBO) {
		WorkTask workTask = workTaskService.getById(workTaskUserBO.getTaskId());
		return new Content(workTask.getName(), "Modify the task leader to:" + UserCacheUtil.getUserName(workTaskUserBO.getUserId()));
	}

	public Content setWorkTaskOwnerUser(WorkTaskOwnerUserBO workTaskOwnerUserBO) {
		List<String> userNames = new ArrayList<>();
		for (String userId : workTaskOwnerUserBO.getOwnerUserId().split(",")) {
			userNames.add(UserCacheUtil.getUserName(Long.valueOf(userId)));
		}
		WorkTask workTask = workTaskService.getById(workTaskOwnerUserBO.getTaskId());
		return new Content(workTask.getName(), "Modify task participants to:" + CollUtil.join(userNames, ","));
	}

	public Content setWorkTaskTime(WorkTask workTask) {
		WorkTask workTask1 = workTaskService.getById(workTask.getTaskId());
		String detail = "";
		if (workTask.getStartTime() != null) {
			detail += "Modify the task start time to:" + DateUtil.formatDate(workTask.getStartTime()) + ".";
		}
		if (workTask.getStopTime() != null) {
			detail += "Modify the end time of the task to:" + DateUtil.formatDate(workTask.getStopTime()) + ".";
		}
		return new Content(workTask1.getName(), detail);
	}

	public Content setWorkTaskLabel(WorkTaskLabelsBO workTaskLabelsBO) {
		List<String> labelNames = new ArrayList<>();
		for (String labelId : workTaskLabelsBO.getLabelId().split(",")) {
			WorkTaskLabel taskLabel = workTaskLabelService.getById(workTaskLabelsBO.getLabelId());
			labelNames.add(taskLabel.getName());
		}
		WorkTask workTask = workTaskService.getById(workTaskLabelsBO.getTaskId());
		return new Content(workTask.getName(), "Modify the task label to:" + CollUtil.join(labelNames, ","));
	}

	public Content setWorkTaskPriority(WorkTaskPriorityBO workTaskPriorityBO) {
		WorkTask workTask = workTaskService.getById(workTaskPriorityBO.getTaskId());
		return new Content(workTask.getName(), "Modify task priority to:" + workTaskService.getPriorityDesc(workTaskPriorityBO.getPriority()));
	}

	public Content addWorkChildTask(WorkTask workTask) {
		WorkTask workTask1 = workTaskService.getById(workTask.getPid());
		return new Content(workTask1.getName(), "Added subtask:" + workTask.getName());
	}

	public Content deleteWorkTaskLabel(WorkTaskLabelBO workTaskLabelBO) {
		WorkTaskLabel taskLabel = workTaskLabelService.getById(workTaskLabelBO.getLabelId());
		WorkTask workTask = workTaskService.getById(workTaskLabelBO.getTaskId());
		return new Content(workTask.getName(), "removed label:" + taskLabel.getName());
	}

	public Content deleteWorkTask(Integer taskId) {
		WorkTask workTask = workTaskService.getById(taskId);
		return new Content(workTask.getName(), "Deleted task:" + workTask.getName());
	}

	public Content deleteWorkChildTask(Integer taskId) {
		WorkTask workTask = workTaskService.getById(taskId);
		return new Content(workTask.getName(), "Subtask deleted:" + workTask.getName());
	}

	public Content archiveByTaskId(Integer taskId) {
		WorkTask workTask = workTaskService.getById(taskId);
		return new Content(workTask.getName(), "Archived task:" + workTask.getName());
	}

	public Content deleteTask(Integer taskId) {
		WorkTask workTask = workTaskService.getById(taskId);
		return new Content(workTask.getName(), "Delete the task completely:" + workTask.getName());
	}

	public Content restore(Integer taskId) {
		WorkTask workTask = workTaskService.getById(taskId);
		return new Content(workTask.getName(), "Restored task from recycle bin:" + workTask.getName());
	}
}
