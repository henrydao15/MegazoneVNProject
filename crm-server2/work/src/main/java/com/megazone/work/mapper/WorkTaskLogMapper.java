package com.megazone.work.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.work.entity.PO.WorkTaskLog;
import com.megazone.work.entity.VO.WorkTaskLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
public interface WorkTaskLogMapper extends BaseMapper<WorkTaskLog> {
	List<WorkTaskLogVO> queryTaskLog(@Param("taskId") Integer taskId);
}
