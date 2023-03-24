package com.megazone.hrm.service.actionrecord.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.exception.CrmException;
import com.megazone.core.utils.TagUtil;
import com.megazone.hrm.common.HrmCodeEnum;
import com.megazone.hrm.constant.*;
import com.megazone.hrm.entity.BO.EliminateCandidateBO;
import com.megazone.hrm.entity.BO.UpdateCandidatePostBO;
import com.megazone.hrm.entity.BO.UpdateCandidateRecruitChannelBO;
import com.megazone.hrm.entity.BO.UpdateCandidateStatusBO;
import com.megazone.hrm.entity.PO.*;
import com.megazone.hrm.service.*;
import com.megazone.hrm.service.actionrecord.AbstractHrmActionRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Candidate operation record
 *
 * @author
 */
@Service("candidateActionRecordService")
@SysLog(subModel = SubModelType.HRM_CANDIDATE)
public class CandidateActionRecordServiceImpl extends AbstractHrmActionRecordService {

	private static HrmActionTypeEnum actionTypeEnum = HrmActionTypeEnum.RECRUIT_CANDIDATE;
	private static LabelGroupEnum labelGroupEnum = LabelGroupEnum.RECRUIT_CANDIDATE;
	/**
	 * attribute kv
	 */
	private static Dict properties = Dict.create().set("candidateName", "candidateName").set("mobile", "mobile").set("sex", "gender").set("age ", "age").set("postId", "Position").set("workTime", "Working Years").set("education", "Education").set("graduateSchool", "Graduate School").set("latestWorkPlace", "latest work place").set("channelId", "Recruitment Channel").set("remark", "Remarks");
	@Autowired
	private IHrmRecruitPostService recruitPostService;
	@Autowired
	private IHrmRecruitCandidateService recruitCandidateService;
	@Autowired
	private IHrmRecruitChannelService recruitChannelService;
	@Autowired
	private IHrmEmployeeService employeeService;
	@Autowired
	private IHrmRecruitInterviewService interviewService;

	/**
	 * Create/delete operation records
	 */
	@SysLogHandler(isReturn = true)
	public Content addOrDeleteRecord(HrmActionBehaviorEnum behaviorEnum, Integer candidateId) {
		String content = "The " + behaviorEnum.getName() + StringUtils.SPACE + labelGroupEnum.getDesc();
		actionRecordService.saveRecord(actionTypeEnum, behaviorEnum, Collections.singletonList(content), candidateId);
		HrmRecruitCandidate candidate = recruitCandidateService.getById(candidateId);
		if (HrmActionBehaviorEnum.ADD.equals(behaviorEnum)) {
			return new Content(candidate.getCandidateName(), content, BehaviorEnum.SAVE);
		} else {
			return new Content(candidate.getCandidateName(), content, BehaviorEnum.DELETE);
		}
	}


	/**
	 * Job entity class modification
	 */
	@SysLogHandler(isReturn = true)
	public Content entityUpdateRecord(Map<String, Object> oldRecord, Map<String, Object> newRecord, Integer candidateId, String candidateName) {
		List<String> contentList = entityCommonUpdateRecord(labelGroupEnum, properties, oldRecord, newRecord);
		actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, contentList, candidateId);
		return new Content(candidateName, CollUtil.join(contentList, ","), BehaviorEnum.UPDATE);
	}

	@Override
	protected String compare(LabelGroupEnum labelGroupEnum, Dict properties, String newFieldKey, String oldValue, String newValue) {
		String content = super.compare(labelGroupEnum, properties, newFieldKey, oldValue, newValue);
		if ("postId".equals(newFieldKey)) {
			HrmRecruitPost oldPost = recruitPostService.getById(oldValue);
			HrmRecruitPost newPost = recruitPostService.getById(newValue);
			if (oldPost != null) {
				oldValue = oldPost.getPostName();
			}
			if (newPost != null) {
				newValue = newPost.getPostName();
			}
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		} else if ("sex".equals(newFieldKey)) {
			if (!"null".equals(oldValue)) {
				oldValue = SexEnum.parseName(Integer.parseInt(oldValue));
			}
			if (!"null".equals(newValue)) {
				newValue = SexEnum.parseName(Integer.parseInt(newValue));
			}
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		} else if ("education".equals(newFieldKey)) {
			if (!"null".equals(oldValue)) {
				oldValue = CandidateEducationEnum.parseName(Integer.parseInt(oldValue));
			}
			if (!"null".equals(newValue)) {
				newValue = CandidateEducationEnum.parseName(Integer.parseInt(newValue));
			}
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		} else if ("channelId".equals(newFieldKey)) {
			HrmRecruitChannel oldChannel = recruitChannelService.getById(oldValue);
			HrmRecruitChannel newChannel = recruitChannelService.getById(newValue);
			if (oldChannel != null) {
				oldValue = oldChannel.getValue();
			}
			if (newChannel != null) {
				newValue = newChannel.getValue();
			}
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		}
		return content;
	}

	/**
	 * Batch modify candidate status operation records
	 * -- move to primary pass 2
	 * --Move to Interview Pass 4
	 * --move to pending 6
	 * -- revert to new candidate 1
	 */
	@SysLogHandler(isReturn = true)
	public List<Content> updateCandidateStatusRecord(UpdateCandidateStatusBO updateCandidateStatusBO) {
		List<Content> contentList = new ArrayList<>();
		List<Integer> candidateIds = updateCandidateStatusBO.getCandidateIds();
		Integer status = updateCandidateStatusBO.getStatus();
		String content = "Move candidate{} to" + CandidateStatusEnum.parseName(status);
		for (Integer candidateId : candidateIds) {
			HrmRecruitCandidate candidate = recruitCandidateService.getById(candidateId);
			String format = StrUtil.format(content, candidate.getCandidateName());
			actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, Collections.singletonList(format), candidateId);
			contentList.add(new Content(candidate.getCandidateName(), format, BehaviorEnum.UPDATE));
		}
		return contentList;
	}

	@SysLogHandler(isReturn = true)
	public List<Content> eliminateCandidateBORecord(EliminateCandidateBO eliminateCandidateBO) {
		List<Content> contentList = new ArrayList<>();
		String content = "Candidate {} eliminated, reason for elimination: " + eliminateCandidateBO.getEliminate();
		if (StrUtil.isNotEmpty(eliminateCandidateBO.getRemarks())) {
			content += ",Remarks:" + eliminateCandidateBO.getRemarks();
		}
		for (Integer candidateId : eliminateCandidateBO.getCandidateIds()) {
			HrmRecruitCandidate candidate = recruitCandidateService.getById(candidateId);
			String format = StrUtil.format(content, candidate.getCandidateName());
			actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, Collections.singletonList(format), candidateId);
			contentList.add(new Content(candidate.getCandidateName(), format, BehaviorEnum.UPDATE));
		}
		return contentList;
	}


	/**
	 * Modify job application records
	 */
	@SysLogHandler(isReturn = true)
	public List<Content> updateCandidatePostRecord(UpdateCandidatePostBO updateCandidatePostBO) {
		List<Content> contentList = new ArrayList<>();
		List<Integer> candidateIds = updateCandidatePostBO.getCandidateIds();
		Integer postId = updateCandidatePostBO.getPostId();
		HrmRecruitPost recruitPost = recruitPostService.getById(postId);
		String content = "Changed job position for candidate {}:" + recruitPost.getPostName();
		for (Integer candidateId : candidateIds) {
			HrmRecruitCandidate candidate = recruitCandidateService.getById(candidateId);
			String format = StrUtil.format(content, candidate.getCandidateName());
			actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, Collections.singletonList(format), candidateId);
			contentList.add(new Content(candidate.getCandidateName(), format, BehaviorEnum.UPDATE));
		}
		return contentList;
	}

	/**
	 * Modify the application channel operation record
	 */
	@SysLogHandler(isReturn = true)
	public List<Content> updateCandidateRecruitChannel(UpdateCandidateRecruitChannelBO updateCandidateRecruitChannelBO) {
		List<Content> contentList = new ArrayList<>();
		List<Integer> candidateIds = updateCandidateRecruitChannelBO.getCandidateIds();
		HrmRecruitChannel channel = recruitChannelService.getById(updateCandidateRecruitChannelBO.getChannelId());
		String recruitChannel = channel.getValue();
		String content = "Changed recruitment channel for candidate {}:" + recruitChannel;
		for (Integer candidateId : candidateIds) {
			HrmRecruitCandidate candidate = recruitCandidateService.getById(candidateId);
			String format = StrUtil.format(content, candidate.getCandidateName());
			actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, Collections.singletonList(format), candidateId);
			contentList.add(new Content(candidate.getCandidateName(), format, BehaviorEnum.UPDATE));
		}
		return contentList;
	}

	/**
	 * Clean up candidate operation records with one click
	 *
	 * @param jsonObject
	 */
	public void cleanCandidateRecord(JSONObject jsonObject) {
		List<Integer> candidateIds = jsonObject.getJSONArray("candidateIds").toJavaList(Integer.class);
		String eliminate = jsonObject.getString("eliminate");
		String content = "Clean up candidates, reason" + eliminate;
		for (Integer candidateId : candidateIds) {
			actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, Collections.singletonList(content), candidateId);
		}
	}

	/**
	 * Arrange interview operation records
	 *
	 * @param interview
	 */
	@SysLogHandler(isReturn = true)
	public Content addInterviewRecord(HrmRecruitInterview interview) {
		HrmRecruitCandidate candidate = recruitCandidateService.getById(interview.getCandidateId());
		HrmEmployee interviewEmployee = employeeService.getById(interview.getInterviewEmployeeId());
		String interviewEmployeeName = "";
		String content = "Interview scheduled for candidate " + candidate.getCandidateName() + ": " + DateUtil.formatDateTime(interview.getInterviewTime());
		if (interview.getType() != null) {
			content += ", interview method: " + InterviewType.parseName(interview.getType());
		}
		if (interviewEmployee != null) {
			interviewEmployeeName = interviewEmployee.getEmployeeName();
			content += ", interviewer: " + interviewEmployeeName;
		}
		Set<Integer> employeeIds = TagUtil.toSet(interview.getOtherInterviewEmployeeIds());
		StringBuilder otherInterviewEmployeeName = new StringBuilder();
		if (CollectionUtil.isNotEmpty(employeeIds)) {
			employeeIds.forEach(employeeId -> {
				HrmEmployee employee = employeeService.getById(employeeId);
				//Judging that the employee does not exist || Delete || has left
				if (employee.getIsDel() == 1 || employee.getEntryStatus() == EmployeeEntryStatus.ALREADY_LEAVE.getValue()) {
					throw new CrmException(HrmCodeEnum.RESULT_CONFIRM_EMPLOYEE_NO_EXIST, employee.getEmployeeName());
				}
				otherInterviewEmployeeName.append(employee.getEmployeeName()).append(",");
			});
		}
		if (StrUtil.isNotEmpty(otherInterviewEmployeeName.toString())) {
			content += ", other interviewers: " + otherInterviewEmployeeName.deleteCharAt(otherInterviewEmployeeName.length() - 1).toString();
		}
		content += ", interview round: " + interview.getStageNum() + " round";
		actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, Collections.singletonList(content), interview.getCandidateId());
		return new Content(candidate.getCandidateName(), content, BehaviorEnum.UPDATE);
	}

	/**
	 * Cancel the interview operation record
	 *
	 * @param interview
	 */
	@SysLogHandler(isReturn = true)
	public Content cancelInterviewRecord(HrmRecruitInterview interview) {
		HrmRecruitCandidate candidate = recruitCandidateService.getById(interview.getCandidateId());
		HrmEmployee interviewEmployee = employeeService.getById(interview.getInterviewEmployeeId());
		String interviewEmployeeName = "";
		if (interviewEmployee != null) {
			interviewEmployeeName = interviewEmployee.getEmployeeName();
		}
		Set<Integer> employeeIds = TagUtil.toSet(interview.getOtherInterviewEmployeeIds());
		StringBuilder otherInterviewEmployeeName = new StringBuilder();
		if (CollectionUtil.isNotEmpty(employeeIds)) {
			employeeIds.forEach(employeeId -> {
				HrmEmployee employee = employeeService.getById(employeeId);
				//Judging that the employee does not exist || Delete || has left
				if (employee.getIsDel() == 1 || employee.getEntryStatus() == EmployeeEntryStatus.ALREADY_LEAVE.getValue()) {
					throw new CrmException(HrmCodeEnum.RESULT_CONFIRM_EMPLOYEE_NO_EXIST, employee.getEmployeeName());
				}
				otherInterviewEmployeeName.append(employee.getEmployeeName()).append(",");
			});
		}
		String content = "Candidate for candidate" + candidate.getCandidateName() + "Cancelled interview:" + DateUtil.formatDateTime(interview.getInterviewTime());
		if (interview.getType() != null) {
			content += ", interview method: " + InterviewType.parseName(interview.getType());
		}
		if (StrUtil.isNotEmpty(interview.getCancelReason())) {
			content += ", reason for cancellation: " + interview.getCancelReason();
		}
		if (StrUtil.isNotEmpty(interviewEmployeeName)) {
			content += ", original interviewer: " + interviewEmployeeName;
		}
		if (StrUtil.isNotEmpty(otherInterviewEmployeeName.toString())) {
			content += ", the original other interviewer: " + otherInterviewEmployeeName.deleteCharAt(otherInterviewEmployeeName.length() - 1).toString();
		}
		content += ", the original interview round: the first" + interview.getStageNum() + "round";
		actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, Collections.singletonList(content), interview.getCandidateId());
		return new Content(candidate.getCandidateName(), content, BehaviorEnum.UPDATE);
	}

	/**
	 * Change the interview schedule record
	 *
	 * @param interview
	 * @param operateType
	 * @return
	 */
	@SysLogHandler(isReturn = true)
	public Content updateInterviewRecord(HrmRecruitInterview interview, Integer operateType) {
		HrmRecruitCandidate candidate = recruitCandidateService.getById(interview.getCandidateId());
		HrmRecruitInterview hrmRecruitInterview = interviewService.getById(interview.getInterviewId());
		StringBuilder content = new StringBuilder();
		content.append("for the candidate" + candidate.getCandidateName() + "changed the interview schedule");
		if (operateType == 3) {
			if (null != interview.getInterviewTime()) {
				content.append(", interview time: " + " changed from nothing to " + DateUtil.formatDateTime(interview.getInterviewTime()));
			}
			if (interview.getType() != null) {
				content.append(", interview method: " + " changed from nothing to " + InterviewType.parseName(interview.getType()));
			}
			if (null != interview.getInterviewEmployeeId()) {
				HrmEmployee interviewEmployee = employeeService.getById(interview.getInterviewEmployeeId());
				if (interviewEmployee != null) {
					content.append(", interviewer: " + " changed from nothing to " + interviewEmployee.getEmployeeName());
				}
			}
			Set<Integer> employeeIds = TagUtil.toSet(interview.getOtherInterviewEmployeeIds());
			StringBuilder otherInterviewEmployeeName = new StringBuilder();
			if (CollectionUtil.isNotEmpty(employeeIds)) {
				employeeIds.forEach(employeeId -> {
					HrmEmployee employee = employeeService.getById(employeeId);
					//Judging that the employee does not exist || Delete || has left
					if (employee.getIsDel() == 1 || employee.getEntryStatus() == EmployeeEntryStatus.ALREADY_LEAVE.getValue()) {
						throw new CrmException(HrmCodeEnum.RESULT_CONFIRM_EMPLOYEE_NO_EXIST, employee.getEmployeeName());
					}
					otherInterviewEmployeeName.append(employee.getEmployeeName()).append(",");
				});
				content.append(", other interviewers: " + " changed from nothing to " + otherInterviewEmployeeName.deleteCharAt(otherInterviewEmployeeName.length() - 1).toString());
			}
		} else {
			if (!interview.getInterviewTime().equals(hrmRecruitInterview.getInterviewTime())) {
				content.append(", interview time: " + " changed from " + DateUtil.formatDateTime(hrmRecruitInterview.getInterviewTime()) + " to " + DateUtil.formatDateTime(interview.getInterviewTime()));
			}
			if (interview.getType() != hrmRecruitInterview.getType()) {
				String oldInterViewType = "none";
				String interViewType = "none";
				if (hrmRecruitInterview.getType() != null) {
					oldInterViewType = InterviewType.parseName(hrmRecruitInterview.getType());
				}
				if (interview.getType() != null) {
					interViewType = InterviewType.parseName(interview.getType());
				}
				content.append(", interview method: " + " changed from " + oldInterViewType + " to " + interViewType);
			}
			String oldInterviewEmployeeName = "none";
			String interviewEmployeeName = "None";
			if (null != hrmRecruitInterview.getInterviewEmployeeId()) {
				HrmEmployee oldInterviewEmployee = employeeService.getById(hrmRecruitInterview.getInterviewEmployeeId());
				if (oldInterviewEmployee != null) {
					oldInterviewEmployeeName = oldInterviewEmployee.getEmployeeName();
				}
			}
			if (null != interview.getInterviewEmployeeId()) {
				HrmEmployee interviewEmployee = employeeService.getById(interview.getInterviewEmployeeId());
				if (interviewEmployee != null) {
					interviewEmployeeName = interviewEmployee.getEmployeeName();
				}
			}
			if (!oldInterviewEmployeeName.equals(interviewEmployeeName)) {
				content.append(", Interviewer: " + " changed from " + oldInterviewEmployeeName + " to " + interviewEmployeeName);
			}

			StringBuilder oldOtherInterviewEmployeeName = new StringBuilder();
			StringBuilder otherInterviewEmployeeName = new StringBuilder();
			Set<Integer> oldEmployeeIds = TagUtil.toSet(hrmRecruitInterview.getOtherInterviewEmployeeIds());
			Set<Integer> employeeIds = TagUtil.toSet(interview.getOtherInterviewEmployeeIds());
			if (CollectionUtil.isNotEmpty(oldEmployeeIds)) {
				oldEmployeeIds.forEach(employeeId -> {
					HrmEmployee employee = employeeService.getById(employeeId);
					//Judging that the employee does not exist || Delete || has left
					if (employee.getIsDel() == 1 || employee.getEntryStatus() == EmployeeEntryStatus.ALREADY_LEAVE.getValue()) {
						throw new CrmException(HrmCodeEnum.RESULT_CONFIRM_EMPLOYEE_NO_EXIST, employee.getEmployeeName());
					}
					oldOtherInterviewEmployeeName.append(employee.getEmployeeName()).append(",");
				});
			}
			if (CollectionUtil.isNotEmpty(employeeIds)) {
				employeeIds.forEach(employeeId -> {
					HrmEmployee employee = employeeService.getById(employeeId);
					// judge Employee does not exist || Delete || Resigned
					if (employee.getIsDel() == 1 || employee.getEntryStatus() == EmployeeEntryStatus.ALREADY_LEAVE.getValue()) {
						throw new CrmException(HrmCodeEnum.RESULT_CONFIRM_EMPLOYEE_NO_EXIST, employee.getEmployeeName());
					}
					otherInterviewEmployeeName.append(employee.getEmployeeName()).append(",");
				});
			}
			if (CollectionUtil.isNotEmpty(employeeIds) && CollectionUtil.isNotEmpty(oldEmployeeIds) && !TagUtil.isSetEqual(oldEmployeeIds, employeeIds)) {
				content.append(", other interviewer: " + "from" + oldOtherInterviewEmployeeName.deleteCharAt(oldOtherInterviewEmployeeName.length() - 1).toString() + "changed to" + otherInterviewEmployeeName.deleteCharAt(otherInterviewEmployeeName.length() - 1).toString());
			} else if (CollectionUtil.isNotEmpty(employeeIds) && CollectionUtil.isEmpty(oldEmployeeIds)) {
				content.append(", other interviewers: " + "changed from " + "none" + "to" + otherInterviewEmployeeName.deleteCharAt(otherInterviewEmployeeName.length() - 1).toString());
			} else if (CollectionUtil.isEmpty(employeeIds) && CollectionUtil.isNotEmpty(oldEmployeeIds)) {
				content.append(", other interviewers: " + "from" + oldOtherInterviewEmployeeName.deleteCharAt(oldOtherInterviewEmployeeName.length() - 1).toString() + "change to" + "none");
			}
		}
		actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, Collections.singletonList(content.toString()), interview.getCandidateId());
		return new Content(candidate.getCandidateName(), content.toString(), BehaviorEnum.UPDATE);
	}
}
