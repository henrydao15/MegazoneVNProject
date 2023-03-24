package com.megazone.work.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.work.entity.PO.WorkTaskLabel;
import com.megazone.work.entity.VO.TaskInfoVO;
import com.megazone.work.entity.VO.WorkTaskByLabelVO;
import com.megazone.work.entity.VO.WorkTaskLabelOrderVO;
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
public interface WorkTaskLabelMapper extends BaseMapper<WorkTaskLabel> {
	List<WorkTaskLabelOrderVO> getLabelList();

	List<TaskInfoVO> getTaskList(@Param("labelId") Integer labelId, @Param("userId") Long userId);

	List<WorkTaskByLabelVO> getWorkList(@Param("labelId") Integer labelId, @Param("userId") Long userId);
}
