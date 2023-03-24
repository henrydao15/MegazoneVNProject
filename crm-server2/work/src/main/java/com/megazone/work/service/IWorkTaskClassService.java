package com.megazone.work.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.work.entity.PO.WorkTaskClass;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
public interface IWorkTaskClassService extends BaseService<WorkTaskClass> {
	public List<WorkTaskClass> queryClassNameByWorkId(Integer workId);

	public void saveWorkTaskClass(WorkTaskClass workTaskClass);

	public void updateWorkTaskClass(WorkTaskClass workTaskClass);
}
