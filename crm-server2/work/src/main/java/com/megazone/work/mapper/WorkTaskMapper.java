package com.megazone.work.mapper;

import cn.hutool.core.lang.Dict;
import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.work.entity.BO.OaTaskListBO;
import com.megazone.work.entity.BO.TaskLabelBO;
import com.megazone.work.entity.BO.WorkTaskLabelBO;
import com.megazone.work.entity.BO.WorkTaskNameBO;
import com.megazone.work.entity.PO.WorkTask;
import com.megazone.work.entity.VO.OaTaskListVO;
import com.megazone.work.entity.VO.TaskDetailVO;
import com.megazone.work.entity.VO.TaskInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-18
 */
public interface WorkTaskMapper extends BaseMapper<WorkTask> {

	List<TaskInfoVO> queryMyTaskList(@Param("isTop") Integer isTop, @Param("userId") Long userId, @Param("data") WorkTaskNameBO workTaskNameBO);

	BasePage<TaskInfoVO> queryMyTaskList(PageEntity page, @Param("isTop") Integer isTop, @Param("userId") Long userId);

	List<TaskLabelBO> queryTaskLabel(@Param("list") List<Integer> taskIdList);

	TaskDetailVO queryTaskInfo(@Param("taskId") Integer taskId);

	List<WorkTaskLabelBO> queryWorkTaskLabelList(@Param("list") List<Integer> labelIdList);

	List<TaskInfoVO> queryTrashList(@Param("userId") Long userId);

	OaTaskListVO queryTaskCount(@Param("data") OaTaskListBO oaTaskListBO, @Param("userIdList") List<Long> userIdList);

	BasePage<TaskInfoVO> getTaskList(BasePage<TaskInfoVO> page, @Param("data") OaTaskListBO oaTaskListBO, @Param("userIdList") List<Long> userIdList);

	List<JSONObject> getTaskListExport(@Param("data") OaTaskListBO oaTaskListBO, @Param("userIdList") List<Long> userIds);

	List<JSONObject> myTaskExport(@Param("data") Dict kv);
}
