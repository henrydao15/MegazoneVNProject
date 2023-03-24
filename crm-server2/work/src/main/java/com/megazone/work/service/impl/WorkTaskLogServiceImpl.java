package com.megazone.work.service.impl;

import cn.hutool.core.date.DateUtil;
import com.megazone.core.exception.CrmException;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.work.common.WorkAuthUtil;
import com.megazone.work.common.WorkCodeEnum;
import com.megazone.work.entity.PO.WorkTaskLog;
import com.megazone.work.entity.VO.WorkTaskLogVO;
import com.megazone.work.mapper.WorkTaskLogMapper;
import com.megazone.work.service.IWorkTaskLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
@Service
public class WorkTaskLogServiceImpl extends BaseServiceImpl<WorkTaskLogMapper, WorkTaskLog> implements IWorkTaskLogService {
	@Override
	public void saveWorkTaskLog(WorkTaskLog workTaskLog) {
		workTaskLog.setCreateTime(DateUtil.date());
		workTaskLog.setLogId(null);
		save(workTaskLog);
	}

	@Override
	public List<WorkTaskLogVO> queryTaskLog(Integer taskId) {
		WorkAuthUtil workAuthUtil = ApplicationContextHolder.getBean(WorkAuthUtil.class);
		if (!workAuthUtil.isWorkAuth(taskId)) {
			throw new CrmException(WorkCodeEnum.WORK_AUTH_ERROR);
		}
		return getBaseMapper().queryTaskLog(taskId);
	}
}
