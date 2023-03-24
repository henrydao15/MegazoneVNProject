package com.megazone.work.common.log;

import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.work.entity.BO.DeleteTaskClassBO;
import com.megazone.work.entity.BO.RemoveWorkOwnerUserBO;
import com.megazone.work.entity.PO.Work;
import com.megazone.work.entity.PO.WorkTask;
import com.megazone.work.entity.PO.WorkTaskClass;
import com.megazone.work.service.IWorkService;
import com.megazone.work.service.IWorkTaskClassService;
import com.megazone.work.service.IWorkTaskService;

public class WorkLog {

	private IWorkService workService = ApplicationContextHolder.getBean(IWorkService.class);

	private IWorkTaskService workTaskService = ApplicationContextHolder.getBean(IWorkTaskService.class);

	private IWorkTaskClassService workTaskClassService = ApplicationContextHolder.getBean(IWorkTaskClassService.class);

	public Content deleteWork(Integer workId) {
		Work work = workService.getById(workId);
		return new Content(work.getName(), "Deleted item:" + work.getName());
	}

	public Content leave(Integer workId) {
		Work work = workService.getById(workId);
		return new Content(work.getName(), "Exited the project:" + work.getName());
	}

	public Content removeWorkOwnerUser(RemoveWorkOwnerUserBO workOwnerUserBO) {
		Work work = workService.getById(workOwnerUserBO.getWorkId());
		return new Content(work.getName(), "Remove project member:" + UserCacheUtil.getUserName(workOwnerUserBO.getOwnerUserId()));
	}

	public Content deleteTaskList(DeleteTaskClassBO deleteTaskClassBO) {
		WorkTaskClass taskClass = workTaskClassService.getById(deleteTaskClassBO.getClassId());
		Work work = workService.getById(deleteTaskClassBO.getWorkId());
		return new Content(work.getName(), "Deleted category:" + taskClass.getName());
	}

	public Content activation(Integer taskId) {
		WorkTask task = workTaskService.getById(taskId);
		return new Content(task.getName(), "Activate the archived task:" + task.getName());
	}
}
