package com.megazone.work.service;

import cn.hutool.core.lang.Dict;
import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.work.entity.BO.*;
import com.megazone.work.entity.PO.Work;
import com.megazone.work.entity.VO.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
public interface IWorkService extends BaseService<Work> {
	public Work addWork(Work work);

	public Work updateWork(Work work);

	public WorkInfoVo queryById(Integer workId);

	public void deleteWork(Integer workId);

	public void leave(Integer workId, Long userId);

	public List<WorkInfoVo> queryWorkNameList(WorkTaskQueryBO workTaskQueryBO);

	public List<TaskInfoVO> queryWorkTaskList(WorkTaskQueryBO workTaskQueryBO);

	public List<WorkTaskTemplateClassVO> queryTaskByWorkId(WorkTaskTemplateBO workTaskTemplateBO);

	public List<WorkTaskTemplateUserVO> queryOwnerTaskListByWorkId(WorkTaskTemplateBO workTaskTemplateBO);

	public BasePage<Work> queryArchiveWorkList(PageEntity pageEntity);

	public WorkStatsVO workStatistics(String workId);

	public List<SimpleUser> queryWorkOwnerList(Integer workId);

	public List<SimpleUser> queryMemberListByWorkOrTask(boolean isWork);

	public void updateOrder(UpdateTaskClassBO updateTaskClassBo);

	public List<WorkOwnerRoleBO> queryOwnerRoleList(Integer workId);

	public List<WorkOwnerRoleBO> setOwnerRole(SetWorkOwnerRoleBO setWorkOwnerRoleBO);

	public void deleteTaskList(DeleteTaskClassBO deleteTaskClassBO);

	public void archiveTask(Integer classId);

	public void archiveTask(ArchiveTaskByOwnerBO archiveTaskByOwnerBO);

	public List<TaskInfoVO> archList(Integer workId);

	public void updateClassOrder(UpdateClassOrderBO updateClassOrderBO);

	public void activation(Integer taskId);

	public JSONObject auth(Integer workId);

	Dict excelImport(MultipartFile file, Integer workId) throws IOException;

	BasePage<FileEntity> queryTaskFileByWorkId(QueryTaskFileByWorkIdBO queryTaskFileByWorkIdBO);

	/**
	 * @param workId ID
	 * @return data
	 */
	public List<Map<String, Object>> workTaskExport(Integer workId);
}
