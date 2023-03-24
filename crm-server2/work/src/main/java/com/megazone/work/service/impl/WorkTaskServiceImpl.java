package com.megazone.work.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminMessageBO;
import com.megazone.core.feign.admin.entity.AdminMessageEnum;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminMessageService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.feign.crm.service.CrmService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.SeparatorUtil;
import com.megazone.core.utils.TagUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.work.common.WorkAuthUtil;
import com.megazone.work.common.WorkCodeEnum;
import com.megazone.work.entity.BO.*;
import com.megazone.work.entity.PO.*;
import com.megazone.work.entity.VO.MyTaskVO;
import com.megazone.work.entity.VO.OaTaskListVO;
import com.megazone.work.entity.VO.TaskDetailVO;
import com.megazone.work.entity.VO.TaskInfoVO;
import com.megazone.work.mapper.WorkTaskMapper;
import com.megazone.work.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-18
 */
@Service
public class WorkTaskServiceImpl extends BaseServiceImpl<WorkTaskMapper, WorkTask> implements IWorkTaskService {
	@Autowired
	private IWorkTaskLogService workTaskLogService;

	@Autowired
	private IWorkTaskLabelService workTaskLabelService;

	@Autowired
	private IWorkTaskRelationService workTaskRelationService;

	@Autowired
	private IWorkTaskClassService workTaskClassService;

	@Autowired
	private IWorkService workService;

	@Autowired
	private WorkAuthUtil workAuthUtil;

	@Autowired
	private AdminFileService adminFileService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CrmService crmService;

	@Autowired
	private IWorkTaskRelationService taskRelationService;

	@Override
	public List<MyTaskVO> myTask(WorkTaskNameBO workTaskNameBO, boolean isInternal) {
		List<MyTaskVO> result = new ArrayList<>();
		result.add(new MyTaskVO("Inbox", 0, 0, new ArrayList<>()));
		if (!isInternal) {
			result.add(new MyTaskVO("Today to do", 1, 0, new ArrayList<>()));
			result.add(new MyTaskVO("Next step", 2, 0, new ArrayList<>()));
			result.add(new MyTaskVO("Do it later", 3, 0, new ArrayList<>()));
		}
		result.forEach(myTaskVO -> {
			Integer userId = UserUtil.getUserId().intValue();
			if (CollUtil.isNotEmpty(workTaskNameBO.getUserIdList())) {
				workTaskNameBO.getUserIdList().remove(userId);
			}
			List<TaskInfoVO> taskList = getBaseMapper().queryMyTaskList(myTaskVO.getIsTop(), UserUtil.getUserId(), workTaskNameBO);
			myTaskVO.setCount(taskList.size());
			if (taskList.size() > 0) {
				if (Objects.equals(workTaskNameBO.getSort(), 1)) {
					taskList.sort(Comparator.comparingInt(TaskInfoVO::getTopOrderNum));
				}
				boolean completedTask = Optional.ofNullable(workTaskNameBO.getCompletedTask()).orElse(false);
				if (completedTask) {
					List<TaskInfoVO> taskInfoVoS = taskList.stream().filter(t -> t.getStatus() == 5).collect(Collectors.toList());
					taskList.removeIf(t -> t.getStatus() == 5);
					taskList.addAll(taskInfoVoS);
				}
				taskList.forEach(taskInfo -> {
					if (taskInfo.getWorkId() != null) {
						Work work = workService.getById(taskInfo.getWorkId());
						if (work != null) {
							taskInfo.setWorkName(work.getName());
						}
					}
					if (taskInfo.getBatchId() != null) {
						List<FileEntity> fileEntities = adminFileService.queryFileList(taskInfo.getBatchId()).getData();
						taskInfo.setFileNum(fileEntities.size());
					}
				});
				taskListTransfer(taskList);
				myTaskVO.setList(taskList);
			}
		});
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void taskListTransfer(List<TaskInfoVO> taskList) {
		List<Integer> taskIdList = taskList.stream().map(TaskInfoVO::getTaskId).collect(Collectors.toList());
		if (taskIdList.size() == 0) {
			taskIdList.add(0);
		}
		List<TaskLabelBO> allLabelList = getBaseMapper().queryTaskLabel(taskIdList);
		Map<Integer, List<TaskLabelBO>> labelMap = allLabelList.stream().collect(Collectors.groupingBy(TaskLabelBO::getTaskId));
		taskList.forEach(taskInfo -> {
			Date stopTime = taskInfo.getStopTime();
			if (stopTime != null) {
				if (stopTime.getTime() < System.currentTimeMillis()) {
					taskInfo.setIsEnd(1);
				} else {
					taskInfo.setIsEnd(0);
				}
			} else {
				taskInfo.setIsEnd(0);
			}
			Integer taskId = taskInfo.getTaskId();
			if (ObjectUtil.isNotEmpty(taskInfo.getMainUserId())) {
				SimpleUser simpleUser = UserCacheUtil.getSimpleUser(taskInfo.getMainUserId());
				taskInfo.setMainUser(simpleUser);
			} else {
				taskInfo.setMainUser(null);
			}
			ArrayList<TaskLabelBO> labelList = new ArrayList<>();
			if (labelMap.get(taskId) != null) {
				labelList.addAll(labelMap.get(taskId));
			}
			taskInfo.setLabelList(labelList);
			int relationCount = 0;
			relationCount += SeparatorUtil.toSet(taskInfo.getCustomerIds()).size();
			relationCount += SeparatorUtil.toSet(taskInfo.getContactsIds()).size();
			relationCount += SeparatorUtil.toSet(taskInfo.getBusinessIds()).size();
			relationCount += SeparatorUtil.toSet(taskInfo.getContractIds()).size();
			taskInfo.setRelationCount(relationCount);
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateTop(UpdateTaskTopBo updateTaskClassBo) {
		if (CollectionUtil.isNotEmpty(updateTaskClassBo.getFromList())) {
			List<Integer> fromList = updateTaskClassBo.getFromList();
			for (int i = 1; i <= fromList.size(); i++) {
				updateById(new WorkTask().setTaskId(fromList.get(i - 1)).setIsTop(updateTaskClassBo.getFromTopId()).setTopOrderNum(i));
			}
		}
		if (CollectionUtil.isNotEmpty(updateTaskClassBo.getToList())) {
			List<Integer> toList = updateTaskClassBo.getToList();
			for (int i = 1; i <= toList.size(); i++) {
				updateById(new WorkTask().setTaskId(toList.get(i - 1)).setIsTop(updateTaskClassBo.getToTopId()).setTopOrderNum(i));
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveWorkTask(WorkTask task) {
		UserInfo user = UserUtil.getUser();
		if (task.getMainUserId() == null) {
			task.setMainUserId(user.getUserId());
		}
		if (StrUtil.isNotEmpty(task.getOwnerUserId())) {
			Set<Long> ownerUserId = SeparatorUtil.toLongSet(task.getOwnerUserId());
			ownerUserId.add(user.getUserId());
			task.setOwnerUserId(SeparatorUtil.fromLongSet(ownerUserId));
		} else {
			task.setOwnerUserId("," + user.getUserId() + ",");
		}
		task.setCreateUserId(user.getUserId());
		if (StrUtil.isEmpty(task.getBatchId())) {
			task.setBatchId(IdUtil.simpleUUID());
		}
		//Label
		String labelId = task.getLabelId();
		if (StrUtil.isNotEmpty(labelId)) {
			task.setLabelId(SeparatorUtil.fromString(labelId));
		}
		boolean isSave = save(task);
		WorkTaskLog workTaskLog = new WorkTaskLog();
		workTaskLog.setUserId(user.getUserId());
		workTaskLog.setTaskId(task.getTaskId());
		workTaskLog.setContent("Added a new task" + task.getName());
		workTaskLogService.saveWorkTaskLog(workTaskLog);
		if (isSave) {
			AdminMessageBO adminMessageBO = new AdminMessageBO();
			adminMessageBO.setMessageType(AdminMessageEnum.OA_TASK_ALLOCATION.getType());
			adminMessageBO.setTypeId(task.getTaskId());
			adminMessageBO.setUserId(user.getUserId());
			adminMessageBO.setTitle(task.getName());
			List<Long> ids = new ArrayList<>();
			if (task.getMainUserId() != null) {
				ids.add(task.getMainUserId());
			}
			adminMessageBO.setIds(ids);
			// task assigned to me
			ApplicationContextHolder.getBean(AdminMessageService.class).sendMessage(adminMessageBO);
			adminMessageBO.setMessageType(AdminMessageEnum.OA_TASK_JOIN.getType());
			ids = new ArrayList<>(StrUtil.splitTrim(task.getOwnerUserId(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList()));
			adminMessageBO.setIds(ids);
			//I am involved in the task
			ApplicationContextHolder.getBean(AdminMessageService.class).sendMessage(adminMessageBO);
			//Subtasks
			List<TaskInfoVO> taskInfoList = task.getTaskInfoList();
			if (CollUtil.isNotEmpty(taskInfoList)) {
				List<WorkTask> childTaskList = new ArrayList<>();
				for (TaskInfoVO taskInfo : taskInfoList) {
					WorkTask childTask = new WorkTask().setName(taskInfo.getName()).setStatus(taskInfo.getStatus()).setStopTime(taskInfo.getStopTime());
					if (taskInfo.getMainUserId() == null) {
						childTask.setMainUserId(user.getUserId());
					} else {
						childTask.setMainUserId(taskInfo.getMainUserId());
					}
					childTask.setOwnerUserId("," + user.getUserId() + ",");
					childTask.setCreateUserId(user.getUserId());
					childTask.setBatchId(task.getBatchId());
					childTask.setPid(task.getTaskId());
					childTaskList.add(childTask);
				}
				saveBatch(childTaskList);
			}
		}
		WorkTaskRelation taskRelation = new WorkTaskRelation();
		if (task.getCustomerIds() != null || task.getContactsIds() != null || task.getBusinessIds() != null || task.getContractIds() != null) {
			taskRelation.setBusinessIds(TagUtil.fromString(task.getBusinessIds()));
			taskRelation.setContactsIds(TagUtil.fromString(task.getContactsIds()));
			taskRelation.setContractIds(TagUtil.fromString(task.getContractIds()));
			taskRelation.setCustomerIds(TagUtil.fromString(task.getCustomerIds()));
			taskRelationService.removeById(task.getTaskId());
			taskRelation.setCreateTime(DateUtil.date());
			taskRelation.setTaskId(task.getTaskId());
			taskRelationService.save(taskRelation);
			crmService.addActivity(2, 11, task.getTaskId());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateWorkTask(WorkTask task) {
		Integer taskId = task.getTaskId();
		WorkTask oldWorkTask = getById(taskId);
		if (oldWorkTask == null) {
			throw new CrmException(WorkCodeEnum.WORK_TASK_EXIST_ERROR);
		}

		//mission name
		if (!Objects.equals(oldWorkTask.getName(), task.getName())) {
			this.saveWorkTaskLog(taskId, "Change the title to:" + task.getName());
		}
		//describe
		if (!Objects.equals(oldWorkTask.getDescription(), task.getDescription())) {
			this.saveWorkTaskLog(taskId, "Modify the description to:" + task.getDescription());
		}
		//principal
		Long mainUserId = task.getMainUserId();
		if (!Objects.equals(oldWorkTask.getMainUserId(), mainUserId)) {
			if (mainUserId == null) {
				this.saveWorkTaskLog(taskId, "Modify the person in charge to: none");
			} else {
				this.saveWorkTaskLog(taskId, "Modify the person in charge to:" + UserCacheUtil.getUserName(mainUserId));
			}
		}
		//task start time
		if (!Objects.equals(oldWorkTask.getStartTime(), task.getStartTime())) {
			this.saveWorkTaskLog(taskId, "Modify the task start time to:" + DateUtil.formatDate(task.getStartTime()));
		}
		//task end time
		if (!Objects.equals(oldWorkTask.getStopTime(), task.getStopTime())) {
			this.saveWorkTaskLog(taskId, "Modify the task end time to:" + DateUtil.formatDate(task.getStopTime()));
		}
		//participant
		if (StrUtil.isEmpty(oldWorkTask.getOwnerUserId())) {
			// Judging that the old data has no participants
			String[] userIds = task.getOwnerUserId().split(",");
			for (String id : userIds) {
				if (StrUtil.isNotBlank(id)) {
					String userName = UserCacheUtil.getUserName(Long.valueOf(id));
					this.saveWorkTaskLog(taskId, "Add" + userName + "Participate in task");
				}
			}
		} else {
			//Determine that the old data has participants
			if (StrUtil.isNotEmpty(task.getOwnerUserId())) {
				String[] userIds = task.getOwnerUserId().split(",");
				List<Long> ids = new ArrayList<>();
				for (String id : userIds) {
					if (StrUtil.isNotBlank(id)) {
						if (!oldWorkTask.getOwnerUserId().contains("," + id + ",")) {
							ids.add(Long.valueOf(id));
							String userName = UserCacheUtil.getUserName(Long.valueOf(id));
							this.saveWorkTaskLog(taskId, "Add" + userName + "Participate in task");
						}
					}
				}
				if (ids.size() > 0) {
					AdminMessageBO adminMessageBO = new AdminMessageBO();
					adminMessageBO.setMessageType(AdminMessageEnum.OA_TASK_JOIN.getType());
					adminMessageBO.setTypeId(taskId);
					adminMessageBO.setUserId(UserUtil.getUserId());
					adminMessageBO.setTitle(task.getName());
					adminMessageBO.setIds(ids);
					ApplicationContextHolder.getBean(AdminMessageService.class).sendMessage(adminMessageBO);
				}
			}
		}
		String ownerUserId = task.getOwnerUserId();
		if (StrUtil.isNotEmpty(ownerUserId)) {
			task.setOwnerUserId(SeparatorUtil.fromString(ownerUserId));
		}
		//Label
		if (StrUtil.isEmpty(oldWorkTask.getLabelId())) {
			//The old data has no label and is added directly
			String[] labelName = task.getLabelId().split(",");
			for (String id : labelName) {
				if (StrUtil.isNotBlank(id)) {
					WorkTaskLabel workTaskLabel = workTaskLabelService.getById(id);
					this.saveWorkTaskLog(taskId, "Added label:" + workTaskLabel.getName());
				}
			}
		} else {
			//The old data has labels and are automatically added or modified
			if (StrUtil.isNotEmpty(task.getLabelId())) {
				String[] labelName = task.getLabelId().split(",");
				for (String id : labelName) {
					if (StrUtil.isNotBlank(id)) {
						if (!oldWorkTask.getLabelId().contains("," + id + ",")) {
							WorkTaskLabel workTaskLabel = workTaskLabelService.getById(id);
							this.saveWorkTaskLog(taskId, "Added label:" + workTaskLabel.getName());
						}
					}
				}
			}
		}
		String labelId = task.getLabelId();
		if (StrUtil.isNotEmpty(labelId)) {
			task.setLabelId(SeparatorUtil.fromString(labelId));
		}
		//priority
		if (!Objects.equals(oldWorkTask.getPriority(), task.getPriority())) {
			this.saveWorkTaskLog(taskId, "Modify the priority to:" + this.getPriorityDesc(task.getPriority()));
		}
		//Associated business
		if (task.getCustomerIds() != null || task.getContactsIds() != null || task.getBusinessIds() != null || task.getContractIds() != null) {
			WorkTaskRelation taskRelation = new WorkTaskRelation();
			taskRelation.setBusinessIds(TagUtil.fromString(task.getBusinessIds()));
			taskRelation.setContactsIds(TagUtil.fromString(task.getContactsIds()));
			taskRelation.setContractIds(TagUtil.fromString(task.getContractIds()));
			taskRelation.setCustomerIds(TagUtil.fromString(task.getCustomerIds()));
			taskRelationService.removeById(task.getTaskId());
			taskRelation.setCreateTime(DateUtil.date());
			taskRelation.setTaskId(task.getTaskId());
			taskRelationService.save(taskRelation);
			crmService.addActivity(2, 11, task.getTaskId());
		}
		//Subtasks
		List<TaskInfoVO> taskInfoList = task.getTaskInfoList();
		if (CollUtil.isNotEmpty(taskInfoList)) {
			LambdaQueryWrapper<WorkTask> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			lambdaQueryWrapper.eq(WorkTask::getPid, taskId);
			lambdaQueryWrapper.eq(WorkTask::getBatchId, task.getBatchId());
			this.remove(lambdaQueryWrapper);
			UserInfo user = UserUtil.getUser();
			List<WorkTask> childTaskList = new ArrayList<>();
			for (TaskInfoVO taskInfo : taskInfoList) {
				WorkTask childTask = new WorkTask().setName(taskInfo.getName()).setStopTime(taskInfo.getStopTime());
				if (taskInfo.getMainUserId() == null) {
					childTask.setMainUserId(user.getUserId());
				} else {
					childTask.setMainUserId(taskInfo.getMainUserId());
				}
				childTask.setOwnerUserId("," + user.getUserId() + ",");
				childTask.setCreateUserId(user.getUserId());
				childTask.setBatchId(task.getBatchId());
				childTask.setPid(taskId);
				childTaskList.add(childTask);
			}
			this.saveBatch(childTaskList);
		}
		return this.updateById(task);
	}


	/**
	 * Save mission log
	 *
	 * @param taskId
	 * @param content
	 * @return void
	 * @date 2020/11/11 16:15
	 **/
	private void saveWorkTaskLog(Integer taskId, String content) {
		WorkTaskLog workTaskLog = new WorkTaskLog();
		workTaskLog.setUserId(UserUtil.getUserId());
		workTaskLog.setTaskId(taskId);
		workTaskLog.setContent(content);
		workTaskLogService.saveWorkTaskLog(workTaskLog);
	}

	/**
	 * Get priority description
	 *
	 * @param priority
	 * @return java.lang.String
	 * @date 2020/11/12 10:22
	 **/
	@Override
	public String getPriorityDesc(Integer priority) {
		String priorityDesc = "";
		switch (priority) {
			case 0:
				priorityDesc = "None";
				break;
			case 1:
				priorityDesc = "Low";
				break;
			case 2:
				priorityDesc = "Medium";
				break;
			case 3:
				priorityDesc = "High";
				break;
			default:
				break;
		}
		return priorityDesc;
	}

	@Override
	public void setWorkTaskStatus(WorkTaskStatusBO workTaskStatusBO) {
		WorkTask workTask = getById(workTaskStatusBO.getTaskId());
		if (workTask.getStatus() != 5 && Objects.equals(5, workTaskStatusBO.getStatus())) {
			AdminMessageBO adminMessageBO = new AdminMessageBO();
			adminMessageBO.setMessageType(AdminMessageEnum.OA_TASK_OVER.getType());
			adminMessageBO.setTypeId(workTask.getTaskId());
			adminMessageBO.setUserId(UserUtil.getUserId());
			adminMessageBO.setTitle(workTask.getName());
			List<Long> ids = new ArrayList<>();
			if (workTask.getMainUserId() != null) {
				ids.add(workTask.getMainUserId());
			}
			ids.addAll(StrUtil.splitTrim(workTask.getOwnerUserId(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList()));
			adminMessageBO.setIds(ids);
			ApplicationContextHolder.getBean(AdminMessageService.class).sendMessage(adminMessageBO);
		}
		workTask.setStatus(workTaskStatusBO.getStatus());
		updateById(workTask);
		String statusName = "";
		if (workTaskStatusBO.getStatus().equals(1)) {
			statusName = "Incomplete";
		} else if (workTaskStatusBO.getStatus().equals(5)) {
			statusName = "Done";
		}
		WorkTaskLog workTaskLog = new WorkTaskLog();
		workTaskLog.setUserId(UserUtil.getUserId());
		workTaskLog.setTaskId(workTaskStatusBO.getTaskId());
		workTaskLog.setContent("Modify the status to: " + statusName);
		workTaskLogService.saveWorkTaskLog(workTaskLog);
	}

	@Override
	public void setWorkTaskTitle(WorkTaskNameBO workTaskNameBO) {
		updateById(new WorkTask().setName(workTaskNameBO.getName()).setTaskId(workTaskNameBO.getTaskId()));
		WorkTaskLog workTaskLog = new WorkTaskLog();
		workTaskLog.setUserId(UserUtil.getUserId());
		workTaskLog.setTaskId(workTaskNameBO.getTaskId());
		workTaskLog.setContent("Modify the title to:" + workTaskNameBO.getName());
		workTaskLogService.saveWorkTaskLog(workTaskLog);
	}

	@Override
	public void setWorkTaskDescription(WorkTaskDescriptionBO workTaskDescriptionBO) {
		updateById(new WorkTask().setDescription(workTaskDescriptionBO.getDescription()).setTaskId(workTaskDescriptionBO.getTaskId()));
		WorkTaskLog workTaskLog = new WorkTaskLog();
		workTaskLog.setUserId(UserUtil.getUserId());
		workTaskLog.setTaskId(workTaskDescriptionBO.getTaskId());
		workTaskLog.setContent("Modify the description to:" + workTaskDescriptionBO.getDescription());
		workTaskLogService.saveWorkTaskLog(workTaskLog);
	}

	@Override
	public void setWorkTaskMainUser(WorkTaskUserBO workTaskUserBO) {
		Long mainUserId = workTaskUserBO.getUserId();
		Integer taskId = workTaskUserBO.getTaskId();
		WorkTaskLog workTaskLog = new WorkTaskLog();
		workTaskLog.setUserId(UserUtil.getUserId());
		workTaskLog.setTaskId(workTaskUserBO.getTaskId());
		if (mainUserId == null) {
			update(null, Wrappers.<WorkTask>lambdaUpdate().set(WorkTask::getMainUserId, null).eq(WorkTask::getTaskId, taskId));
			workTaskLog.setContent("Modify the person in charge to: None");
		} else {
			updateById(new WorkTask().setTaskId(taskId).setMainUserId(mainUserId));
			workTaskLog.setContent("Modify the person in charge to: " + UserCacheUtil.getUserName(mainUserId));
		}
		workTaskLogService.saveWorkTaskLog(workTaskLog);
	}

	@Override
	public void setWorkTaskOwnerUser(WorkTaskOwnerUserBO workTaskOwnerUserBO) {
		WorkTask auldTask = getById(workTaskOwnerUserBO.getTaskId());
		if (StrUtil.isEmpty(auldTask.getOwnerUserId())) {
			// Judging that the old data has no participants
			String[] userIds = workTaskOwnerUserBO.getOwnerUserId().split(",");
			for (String id : userIds) {
				if (StrUtil.isNotBlank(id)) {
					String userName = UserCacheUtil.getUserName(Long.valueOf(id));
					WorkTaskLog workTaskLog = new WorkTaskLog();
					workTaskLog.setUserId(UserUtil.getUserId());
					workTaskLog.setTaskId(workTaskOwnerUserBO.getTaskId());
					workTaskLog.setContent("Add" + userName + "Participate in the task");
					workTaskLogService.saveWorkTaskLog(workTaskLog);
				}
			}
		} else {
			//Determine that the old data has participants
			if (StrUtil.isNotEmpty(workTaskOwnerUserBO.getOwnerUserId())) {
				String[] userIds = workTaskOwnerUserBO.getOwnerUserId().split(",");
				List<Long> ids = new ArrayList<>();
				for (String id : userIds) {
					if (StrUtil.isNotBlank(id)) {
						if (!auldTask.getOwnerUserId().contains("," + id + ",")) {
							ids.add(Long.valueOf(id));
							String userName = UserCacheUtil.getUserName(Long.valueOf(id));
							WorkTaskLog workTaskLog = new WorkTaskLog();
							workTaskLog.setUserId(UserUtil.getUserId());
							workTaskLog.setTaskId(workTaskOwnerUserBO.getTaskId());
							workTaskLog.setContent("Add" + userName + "Participate in the task");
							workTaskLogService.saveWorkTaskLog(workTaskLog);
						}
					}
				}
				if (ids.size() > 0) {
					AdminMessageBO adminMessageBO = new AdminMessageBO();
					adminMessageBO.setMessageType(AdminMessageEnum.OA_TASK_JOIN.getType());
					adminMessageBO.setTypeId(auldTask.getTaskId());
					adminMessageBO.setUserId(UserUtil.getUserId());
					adminMessageBO.setTitle(auldTask.getName());
					adminMessageBO.setIds(ids);
					ApplicationContextHolder.getBean(AdminMessageService.class).sendMessage(adminMessageBO);
				}
			}
		}
		WorkTask workTask = new WorkTask();
		String ownerUserId = workTaskOwnerUserBO.getOwnerUserId();
		if (StrUtil.isNotEmpty(ownerUserId)) {
			workTask.setOwnerUserId(SeparatorUtil.fromString(ownerUserId));
		}
		updateById(workTask.setTaskId(workTaskOwnerUserBO.getTaskId()));
	}

	@Override
	public void setWorkTaskTime(WorkTask workTask) {
		if (workTask.getStartTime() == null && workTask.getStopTime() == null) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		LambdaUpdateChainWrapper<WorkTask> lambdaUpdate = lambdaUpdate();
		if (workTask.getStartTime() != null) {
			WorkTaskLog workTaskLog = new WorkTaskLog();
			workTaskLog.setUserId(UserUtil.getUserId());
			workTaskLog.setTaskId(workTask.getTaskId());
			workTaskLog.setContent("Modify the task start time to:" + DateUtil.formatDate(workTask.getStartTime()));
			workTaskLogService.saveWorkTaskLog(workTaskLog);
			lambdaUpdate.set(WorkTask::getStartTime, workTask.getStartTime());
		}
		if (workTask.getStopTime() != null) {
			WorkTaskLog workTaskLog = new WorkTaskLog();
			workTaskLog.setUserId(UserUtil.getUserId());
			workTaskLog.setTaskId(workTask.getTaskId());
			workTaskLog.setContent("Modify the task end time to:" + DateUtil.formatDate(workTask.getStopTime()));
			workTaskLogService.saveWorkTaskLog(workTaskLog);
			lambdaUpdate.set(WorkTask::getStopTime, workTask.getStopTime());
			if (workTask.getStopTime().getTime() < System.currentTimeMillis()) {
				lambdaUpdate.set(WorkTask::getStatus, 2);
			} else if (workTask.getStopTime().getTime() > System.currentTimeMillis()) {
				lambdaUpdate.set(WorkTask::getStatus, 1);
			}
		}
		lambdaUpdate.eq(WorkTask::getTaskId, workTask.getTaskId());
		lambdaUpdate.update();
	}

	@Override
	public void setWorkTaskLabel(WorkTaskLabelsBO workTaskLabelsBO) {
		WorkTask auldTask = getById(workTaskLabelsBO.getTaskId());
		if (StrUtil.isEmpty(auldTask.getLabelId())) {
			//The old data has no label and is added directly
			String[] labelName = workTaskLabelsBO.getLabelId().split(",");
			for (String id : labelName) {
				if (StrUtil.isNotBlank(id)) {
					WorkTaskLabel workTaskLabel = workTaskLabelService.getById(id);
					WorkTaskLog workTaskLog = new WorkTaskLog();
					workTaskLog.setUserId(UserUtil.getUserId());
					workTaskLog.setTaskId(workTaskLabelsBO.getTaskId());
					workTaskLog.setContent("Added label:" + workTaskLabel.getName());
					workTaskLogService.saveWorkTaskLog(workTaskLog);
				}
			}
		} else {
			//The old data has labels and are automatically added or modified
			if (StrUtil.isNotEmpty(workTaskLabelsBO.getLabelId())) {
				String[] labelName = workTaskLabelsBO.getLabelId().split(",");
				for (String id : labelName) {
					if (StrUtil.isNotBlank(id)) {
						if (!auldTask.getLabelId().contains("," + id + ",")) {
							WorkTaskLabel workTaskLabel = workTaskLabelService.getById(id);
							WorkTaskLog workTaskLog = new WorkTaskLog();
							workTaskLog.setUserId(UserUtil.getUserId());
							workTaskLog.setTaskId(workTaskLabelsBO.getTaskId());
							workTaskLog.setContent("Added label:" + workTaskLabel.getName());
							workTaskLogService.saveWorkTaskLog(workTaskLog);
						}
					}
				}
			}
		}
		WorkTask workTask = new WorkTask();
		String labelId = workTaskLabelsBO.getLabelId();
		if (StrUtil.isNotEmpty(labelId)) {
			workTask.setLabelId(SeparatorUtil.fromString(labelId));
		}
		updateById(workTask.setTaskId(workTaskLabelsBO.getTaskId()));
	}

	@Override
	public void setWorkTaskPriority(WorkTaskPriorityBO workTaskPriorityBO) {
		updateById(new WorkTask().setTaskId(workTaskPriorityBO.getTaskId()).setPriority(workTaskPriorityBO.getPriority()));
		String priority = this.getPriorityDesc(workTaskPriorityBO.getPriority());
		WorkTaskLog workTaskLog = new WorkTaskLog();
		workTaskLog.setUserId(UserUtil.getUserId());
		workTaskLog.setTaskId(workTaskPriorityBO.getTaskId());
		workTaskLog.setContent("Modify the priority to: " + priority);
		workTaskLogService.saveWorkTaskLog(workTaskLog);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public WorkTask addWorkChildTask(WorkTask workTask) {
		WorkTask task = new WorkTask().setName(workTask.getName()).setStopTime(workTask.getStopTime());
		WorkTask parentTask = getById(workTask.getPid());
		task.setIshidden(parentTask.getIshidden());
		UserInfo user = UserUtil.getUser();
		if (workTask.getMainUserId() == null) {
			task.setMainUserId(user.getUserId());
		} else {
			task.setMainUserId(workTask.getMainUserId());
		}
		task.setOwnerUserId("," + user.getUserId() + ",");
		task.setCreateUserId(user.getUserId());
		task.setBatchId(IdUtil.simpleUUID());
		task.setPid(workTask.getPid());
		save(task);
		WorkTaskLog workTaskLog = new WorkTaskLog();
		workTaskLog.setUserId(user.getUserId());
		workTaskLog.setTaskId(task.getTaskId());
		workTaskLog.setContent("Added a task" + task.getName());
		workTaskLogService.saveWorkTaskLog(workTaskLog);
		return task;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateWorkChildTask(WorkTask workTask) {
		WorkTask task = new WorkTask().setTaskId(workTask.getTaskId()).setStopTime(workTask.getStopTime()).setName(workTask.getName());
		if (workTask.getMainUserId() == null) {
			update(null, Wrappers.<WorkTask>lambdaUpdate().set(WorkTask::getMainUserId, null).eq(WorkTask::getTaskId, workTask.getTaskId()));
		} else {
			task.setMainUserId(workTask.getMainUserId());
		}
		updateById(task.setTaskId(workTask.getTaskId()));
	}

	@Override
	public void setWorkChildTaskStatus(WorkTaskStatusBO workTaskStatusBO) {
		updateById(new WorkTask().setStatus(workTaskStatusBO.getStatus()).setTaskId(workTaskStatusBO.getTaskId()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteWorkTaskOwnerUser(WorkTaskUserBO workTaskUserBO) {
		WorkTask task = getById(workTaskUserBO.getTaskId());
		Long userId = workTaskUserBO.getUserId();
		String userName = UserCacheUtil.getUserName(userId);
		Set<Long> ownerUserIds = SeparatorUtil.toLongSet(task.getOwnerUserId());
		if (StrUtil.isEmpty(userName)) {
			throw new CrmException(WorkCodeEnum.WORK_USER_EXIST_ERROR);
		}
		ownerUserIds.remove(userId);
		task.setOwnerUserId(SeparatorUtil.fromLongSet(ownerUserIds));
		WorkTaskLog workTaskLog = new WorkTaskLog();
		workTaskLog.setUserId(UserUtil.getUserId());
		workTaskLog.setTaskId(task.getTaskId());
		workTaskLog.setContent("Remove " + userName + " from the task");
		workTaskLogService.saveWorkTaskLog(workTaskLog);
		updateById(task);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteWorkTaskLabel(WorkTaskLabelBO workTaskLabelBO) {
		WorkTask task = getById(workTaskLabelBO.getTaskId());
		Integer labelId = workTaskLabelBO.getLabelId();
		WorkTaskLabel taskLabel = workTaskLabelService.getById(labelId);
		Set<Integer> labelIds = SeparatorUtil.toSet(task.getLabelId());
		if (!labelIds.contains(labelId)) {
			throw new CrmException(WorkCodeEnum.WORK_TASK_LABEL_EXIST_ERROR);
		}
		labelIds.remove(labelId);
		task.setLabelId(SeparatorUtil.fromSet(labelIds));
		WorkTaskLog workTaskLog = new WorkTaskLog();
		workTaskLog.setUserId(UserUtil.getUserId());
		workTaskLog.setTaskId(task.getTaskId());
		workTaskLog.setContent("Remove the label " + taskLabel.getName());
		workTaskLogService.saveWorkTaskLog(workTaskLog);
		updateById(task);
	}

	@Override
	public TaskDetailVO queryTaskInfo(Integer taskId) {
		int number = query().eq("ishidden", 0).eq("task_id", taskId).count();
		if (number == 0) {
			throw new CrmException(WorkCodeEnum.WORK_TASK_DELETE_ERROR);
		}
		return queryTrashTaskInfo(taskId);
	}

	@Override
	public TaskDetailVO queryTrashTaskInfo(Integer taskId) {
		if (!workAuthUtil.isTaskAuth(taskId)) {
			throw new CrmException(WorkCodeEnum.WORK_AUTH_ERROR);
		}
		if (!UserUtil.isAdmin() && !UserUtil.getUser().getRoles().contains(workAuthUtil.getWorkAdminRole())) {
			WorkTask task = getById(taskId);
			boolean auth = true;
			if (Objects.equals(0, task.getWorkId())) {
				auth = workAuthUtil.isOaAuth(1, taskId);
			} else {
				Work work = workService.getById(task.getWorkId());
				if (TagUtil.toLongSet(work.getOwnerUserId()).contains(UserUtil.getUserId()) || work.getIsOpen() == 1) {
					auth = false;
				}
			}
			if (auth) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
		}
		TaskDetailVO taskDetailVO = transfer(taskId);
		if (StrUtil.isNotEmpty(taskDetailVO.getBatchId())) {
			List<FileEntity> data = adminFileService.queryFileList(taskDetailVO.getBatchId()).getData();
			taskDetailVO.setFile(data);
		} else {
			taskDetailVO.setFile(new ArrayList<>());
		}
		List<Integer> childTaskIdList = listObjs(new QueryWrapper<WorkTask>().select("task_id").eq("pid", taskId), o -> Integer.valueOf(o.toString()));
		List<TaskDetailVO> childTaskList = new ArrayList<>();
		if (CollectionUtil.isNotEmpty(childTaskIdList)) {
			childTaskIdList.forEach(childTaskId -> {
				TaskDetailVO childTask = transfer(childTaskId);
				if (StrUtil.isNotEmpty(taskDetailVO.getBatchId())) {
					List<FileEntity> file = adminFileService.queryFileList(taskDetailVO.getBatchId()).getData();
					childTask.setFile(file);
				}
				childTaskList.add(childTask);
			});
		}
		taskDetailVO.setChildTask(childTaskList);
		if (taskDetailVO.getWorkId() != null && taskDetailVO.getWorkId() != 0) {
			taskDetailVO.setAuthList(ApplicationContextHolder.getBean(IWorkService.class).auth(taskDetailVO.getWorkId()).getJSONObject("work"));
		}
		setRelation(taskId, taskDetailVO);
		return taskDetailVO;
	}

	private void setRelation(Integer taskId, TaskDetailVO taskDetailVO) {
		WorkTaskRelation relation = workTaskRelationService.lambdaQuery().eq(WorkTaskRelation::getTaskId, taskId).one();
		if (relation != null) {
			List<String> customerIds = StrUtil.splitTrim(relation.getCustomerIds(), Const.SEPARATOR);
			if (customerIds.size() > 0) {
				List<SimpleCrmEntity> data = crmService.queryCustomerInfo(customerIds).getData();
				taskDetailVO.setCustomerList(data);
			}
			List<String> contactsIds = StrUtil.splitTrim(relation.getContactsIds(), Const.SEPARATOR);
			if (contactsIds.size() > 0) {
				List<SimpleCrmEntity> data = crmService.queryContactsInfo(contactsIds).getData();
				taskDetailVO.setContactsList(data);
			}
			List<String> businessIds = StrUtil.splitTrim(relation.getBusinessIds(), Const.SEPARATOR);
			if (businessIds.size() > 0) {
				List<SimpleCrmEntity> data = crmService.queryBusinessInfo(businessIds).getData();
				taskDetailVO.setBusinessList(data);
			}
			List<String> contractIds = StrUtil.splitTrim(relation.getContractIds(), Const.SEPARATOR);
			if (contractIds.size() > 0) {
				List<SimpleCrmEntity> data = crmService.queryContractInfo(contractIds).getData();
				taskDetailVO.setContractList(data);
			}
		}
	}

	private TaskDetailVO transfer(Integer taskId) {
		TaskDetailVO task = getBaseMapper().queryTaskInfo(taskId);
		task.setStopTime(DateUtil.formatDate((Date) task.getStopTime()));
		if (task.getMainUserId() != null) {
			task.setMainUser(UserCacheUtil.getSimpleUser(task.getMainUserId()));
		}
		task.setCreateUser(UserCacheUtil.getSimpleUser(task.getCreateUserId()));
		List<WorkTaskLabelBO> labelList = new ArrayList<>();
		List<SimpleUser> ownerUserList = new ArrayList<>();
		if (StrUtil.isNotBlank(task.getLabelId())) {
			List<String> labelIds = StrUtil.splitTrim(task.getLabelId(), Const.SEPARATOR);
			if (labelIds.size() > 0) {
				labelList = getBaseMapper().queryWorkTaskLabelList(labelIds.stream().map(Integer::parseInt).collect(Collectors.toList()));
			}
		}
		List<Long> ids = StrUtil.splitTrim(task.getOwnerUserId(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList());
		if (ids.size() > 0) {
			ownerUserList.addAll(UserCacheUtil.getSimpleUsers(ids));
		}
		setRelation(taskId, task);
		task.setOwnerUserList(ownerUserList);
		task.setLabelList(labelList);
		return task;
	}

	@Override
	public void deleteWorkTask(Integer taskId) {
		WorkTask workTask = getOne(new QueryWrapper<WorkTask>().select("pid").eq("task_id", taskId));
		if (!Objects.equals(workTask.getPid(), 0)) {
			removeById(taskId);
		} else {
			UpdateWrapper<WorkTask> updateWrapper = new UpdateWrapper<>();
			updateWrapper.lambda().set(WorkTask::getIshidden, 1);
			updateWrapper.lambda().eq(WorkTask::getPid, taskId);
			this.update(updateWrapper);
			updateById(new WorkTask().setTaskId(taskId).setIshidden(1).setHiddenTime(new Date()));
		}
		if (StrUtil.isNotEmpty(workTask.getBatchId())) {
			adminFileService.delete(workTask.getBatchId());
		}
	}

	@Override
	public void archiveByTaskId(Integer taskId) {
		updateById(new WorkTask().setTaskId(taskId).setIsArchive(1).setArchiveTime(new Date()));
	}

	@Override
	public List<TaskInfoVO> queryTrashList() {
		List<TaskInfoVO> taskInfoVOList;
		if (workAuthUtil.isWorkAdmin()) {
			taskInfoVOList = getBaseMapper().queryTrashList(null);
		} else {
			taskInfoVOList = getBaseMapper().queryTrashList(UserUtil.getUserId());
		}
		taskListTransfer(taskInfoVOList);
		return taskInfoVOList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteTask(Integer taskId) {
		WorkTask task = getById(taskId);
		if (task == null) {
			throw new CrmException(WorkCodeEnum.WORK_TASK_EXIST_ERROR);
		}
		if (task.getIshidden() != 1) {
			throw new CrmException(WorkCodeEnum.WORK_TASK_IN_TRASH_ERROR);
		}
		if (StrUtil.isNotEmpty(task.getBatchId())) {
			adminFileService.delete(task.getBatchId());
		}
// Db.delete("delete from wk_crm_activity where type = 2 and activity_type = 11 and activity_type_id = ?",taskId);
		removeById(taskId);
		lambdaUpdate().eq(WorkTask::getPid, taskId).remove();

	}

	@Override
	public void restore(Integer taskId) {
		WorkTask task = getById(taskId);
		if (task == null) {
			throw new CrmException(WorkCodeEnum.WORK_TASK_EXIST_ERROR);
		}
		if (task.getIshidden() != 1) {
			throw new CrmException(WorkCodeEnum.WORK_TASK_IN_TRASH_ERROR);
		}
		int count = workTaskClassService.count(new QueryWrapper<WorkTaskClass>().eq("class_id", task.getClassId()));
		if (count > 0) {
			update(null, Wrappers.<WorkTask>lambdaUpdate().set(WorkTask::getIshidden, 0).set(WorkTask::getHiddenTime, null).eq(WorkTask::getTaskId, taskId));
		} else {
			update(null, Wrappers.<WorkTask>lambdaUpdate().set(WorkTask::getClassId, -1).set(WorkTask::getIshidden, 0).set(WorkTask::getHiddenTime, null).eq(WorkTask::getTaskId, taskId));
		}
	}

	@Override
	public OaTaskListVO queryTaskList(OaTaskListBO oaTaskListBO) {
		Long userId = oaTaskListBO.getUserId();
		Integer mold = oaTaskListBO.getMold();
		List<Long> userIds = new ArrayList<>();
		if (mold == null) {
			userIds.add(UserUtil.getUserId());
		} else if (mold == 1 && userId == null) {
			userIds = adminService.queryChildUserId(UserUtil.getUserId()).getData();
		} else {
			List<Long> list = adminService.queryChildUserId(UserUtil.getUserId()).getData();
			for (Long id : list) {
				if (id.equals(userId)) {
					userIds.add(userId);
				}
			}
		}
		if (UserUtil.isAdmin() && oaTaskListBO.getType() == 0 && mold == null) {
			userIds = adminService.queryUserList(1).getData();
		}
		if (userIds.size() == 0) {
			return new OaTaskListVO(0, 0, new BasePage<>(), null);
		} else {
			OaTaskListVO oaTaskListVO = getBaseMapper().queryTaskCount(oaTaskListBO, userIds);
			if (oaTaskListBO.getIsExport() != null && oaTaskListBO.getIsExport()) {
				List<JSONObject> recordList = getBaseMapper().getTaskListExport(oaTaskListBO, userIds);
				List<Map<String, Object>> list = new ArrayList<>();
				recordList.forEach(r -> {
					String ownerUserName = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(r.getString("ownerUserId"))).stream().map(SimpleUser::getRealname).collect(Collectors.joining(","));
					r.put("ownerUserName", ownerUserName);
					String mainUserName = UserCacheUtil.getUserName(r.getLong("mainUserId"));
					r.put("mainUserName", mainUserName);
					String createUserName = UserCacheUtil.getUserName(r.getLong("createUserId"));
					r.put("createUserName", createUserName);

					//Splicing related business content
					String relateCrmWork = "";
					if (StrUtil.isNotEmpty(r.getString("customerIds"))) {
						String customerName = crmService.queryCustomerInfo(TagUtil.toSet(r.getString("customerIds"))).getData().stream().map(SimpleCrmEntity::getName).collect(Collectors.joining(","));
						relateCrmWork = relateCrmWork + "customer [" + customerName + "]\n";
					}
					if (StrUtil.isNotEmpty(r.getString("contactsIds"))) {
						String contactsName = crmService.queryContactsInfo(TagUtil.toSet(r.getString("contactsIds"))).getData().stream().map(SimpleCrmEntity::getName).collect(Collectors.joining(","));
						relateCrmWork = relateCrmWork + "contact [" + contactsName + "]\n";
					}
					if (StrUtil.isNotEmpty(r.getString("businessIds"))) {
						String businessName = crmService.queryBusinessInfo(TagUtil.toSet(r.getString("businessIds"))).getData().stream().map(SimpleCrmEntity::getName).collect(Collectors.joining(","));
						relateCrmWork = relateCrmWork + "Business Opportunity [" + businessName + "]\n";
					}
					if (StrUtil.isNotEmpty(r.getString("contractIds"))) {
						String contractName = crmService.queryContractInfo(TagUtil.toSet(r.getString("contractIds"))).getData().stream().map(SimpleCrmEntity::getName).collect(Collectors.joining(","));
						relateCrmWork = relateCrmWork + "Contract [" + contractName + "]\n";
					}
					r.put("relateCrmWork", relateCrmWork);
					list.add(r.getInnerMapObject());
				});
				oaTaskListVO.setExportList(list);
				return oaTaskListVO;
			}
			BasePage<TaskInfoVO> page = getBaseMapper().getTaskList(oaTaskListBO.parse(), oaTaskListBO, userIds);
			page.setList(queryUser(page.getList()));
			return oaTaskListVO.setPage(page);
		}
	}

	private List<TaskInfoVO> queryUser(List<TaskInfoVO> tasks) {
		ArrayList<TaskLabelBO> labelList;
		for (TaskInfoVO task : tasks) {
			Long mainUserId = task.getMainUserId();
			if (mainUserId != null) {
				SimpleUser mainUser = UserCacheUtil.getSimpleUser(mainUserId);
				task.setMainUser(mainUser);
			}
			labelList = new ArrayList<>();
			if (StrUtil.isNotBlank(task.getLabelId())) {
				List<String> list = StrUtil.splitTrim(task.getLabelId(), Const.SEPARATOR);
				List<WorkTaskLabel> taskLabelList = workTaskLabelService.query().select("label_id", "name", "color").in("label_id", list).list();
				List<TaskLabelBO> collect = taskLabelList.stream().map(label -> {
					TaskLabelBO taskLabelBO = new TaskLabelBO();
					taskLabelBO.setColor(label.getColor());
					taskLabelBO.setLabelName(label.getName());
					taskLabelBO.setLabelId(label.getLabelId());
					return taskLabelBO;
				}).collect(Collectors.toList());
				labelList.addAll(collect);
			}
			if (StrUtil.isNotBlank(task.getOwnerUserId())) {
				List<Long> ids = StrUtil.splitTrim(task.getOwnerUserId(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList());
				if (ids.size() > 0) {
					task.setOwnerUserList(UserCacheUtil.getSimpleUsers(ids));
				}
			}
			WorkTaskRelation workTaskRelation = workTaskRelationService.query().eq("task_id", task.getTaskId()).one();
			Integer start = 0;
			if (workTaskRelation != null) {
				start = queryCount(start, workTaskRelation.getBusinessIds());
				start = queryCount(start, workTaskRelation.getContactsIds());
				start = queryCount(start, workTaskRelation.getContractIds());
				start = queryCount(start, workTaskRelation.getCustomerIds());
			}
			task.setRelationCount(start);
			if (task.getStopTime() != null) {
				Calendar date = Calendar.getInstance();
				date.setTime(DateUtil.date());
				//set start time
				Calendar begin = Calendar.getInstance();
				begin.setTime(task.getStopTime());
				if (date.after(begin) && task.getStatus() != 5 && task.getStatus() != 2) {
					task.setIsEnd(1);
				} else {
					task.setIsEnd(0);
				}
			} else {
				task.setIsEnd(0);
			}
			task.setLabelList(labelList);

		}
		return tasks;
	}

	private Integer queryCount(Integer start, String str) {
		// start start number
		if (str != null) {
			String[] ownerUserIds = str.split(",");
			for (String ownerUserId : ownerUserIds) {
				if (StrUtil.isNotBlank(ownerUserId)) {
					++start;
				}
			}
		}
		return start;
	}

	@Override
	public List<Map<String, Object>> workBenchTaskExport() {
		Dict kv = Dict.create().set("userId", UserUtil.getUserId());
		List<JSONObject> recordList = getBaseMapper().myTaskExport(kv);
		List<Map<String, Object>> list = new ArrayList<>();
		recordList.forEach(record -> {
			Long createUserId = record.getLong("createUserId");
			record.put("createUserName", UserCacheUtil.getUserName(createUserId));
			Long mainUserId = record.getLong("mainUserId");
			record.put("mainUserName", UserCacheUtil.getUserName(mainUserId));
			String ownerUserId = record.getString("ownerUserId");
			record.put("ownerUserName", UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(ownerUserId)).stream().map(SimpleUser::getRealname).collect(Collectors.joining(",")));
			//Splicing related business content
			String relateCrmWork = "";
			if (StrUtil.isNotEmpty(record.getString("customerIds"))) {
				relateCrmWork = relateCrmWork + "Customer [" + record.getString("customerName") + "]\n";
			}
			if (StrUtil.isNotEmpty(record.getString("contactsIds"))) {
				relateCrmWork = relateCrmWork + "contacts [" + record.getString("contactsName") + "]\n";
			}
			if (StrUtil.isNotEmpty(record.getString("businessIds"))) {
				relateCrmWork = relateCrmWork + "Business opportunity [" + record.getString("businessName") + "]\n";
			}
			if (StrUtil.isNotEmpty(record.getString("contractIds"))) {
				relateCrmWork = relateCrmWork + "Contract [" + record.getString("contractName") + "]\n";
			}
			record.put("relateCrmWork", relateCrmWork);
			list.add(record.getInnerMapObject());
		});
		return list;
	}
}
