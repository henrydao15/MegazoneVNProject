package com.megazone.work.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.work.entity.PO.WorkTaskLog;
import com.megazone.work.entity.VO.WorkTaskLogVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
public interface IWorkTaskLogService extends BaseService<WorkTaskLog> {

	public void saveWorkTaskLog(WorkTaskLog workTaskLog);

	public List<WorkTaskLogVO> queryTaskLog(Integer taskId);
}
