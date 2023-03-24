package com.megazone.work.common.log;

import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.work.entity.PO.WorkTaskLabel;
import com.megazone.work.service.IWorkTaskLabelService;

public class WorkTaskLabelLog {
	private IWorkTaskLabelService workTaskLabelService = ApplicationContextHolder.getBean(IWorkTaskLabelService.class);

	public Content deleteLabel(Integer labelId) {
		WorkTaskLabel taskLabel = workTaskLabelService.getById(labelId);
		return new Content(taskLabel.getName(), "removed label" + taskLabel.getName());
	}
}
