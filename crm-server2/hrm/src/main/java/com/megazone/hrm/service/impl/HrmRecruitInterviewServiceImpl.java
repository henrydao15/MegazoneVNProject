package com.megazone.hrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminMessage;
import com.megazone.core.feign.admin.entity.AdminMessageEnum;
import com.megazone.core.feign.admin.service.AdminMessageService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.TagUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.hrm.common.HrmCodeEnum;
import com.megazone.hrm.constant.CandidateStatusEnum;
import com.megazone.hrm.constant.InterviewType;
import com.megazone.hrm.constant.RecruitEnum;
import com.megazone.hrm.entity.BO.SetInterviewResultBO;
import com.megazone.hrm.entity.BO.SetRecruitInterviewBO;
import com.megazone.hrm.entity.PO.HrmRecruitCandidate;
import com.megazone.hrm.entity.PO.HrmRecruitInterview;
import com.megazone.hrm.mapper.HrmRecruitInterviewMapper;
import com.megazone.hrm.service.IHrmRecruitCandidateService;
import com.megazone.hrm.service.IHrmRecruitInterviewService;
import com.megazone.hrm.service.actionrecord.impl.CandidateActionRecordServiceImpl;
import com.megazone.hrm.utils.EmployeeCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Service
public class HrmRecruitInterviewServiceImpl extends BaseServiceImpl<HrmRecruitInterviewMapper, HrmRecruitInterview> implements IHrmRecruitInterviewService {

	@Autowired
	private IHrmRecruitCandidateService recruitCandidateService;

	@Resource
	private CandidateActionRecordServiceImpl candidateActionRecordService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setInterview(SetRecruitInterviewBO setRecruitInterviewBO) {
		Integer operateType = 1;
		HrmRecruitInterview hrmRecruitInterview = BeanUtil.copyProperties(setRecruitInterviewBO, HrmRecruitInterview.class);
		HrmRecruitCandidate hrmRecruitCandidate = recruitCandidateService.getById(hrmRecruitInterview.getCandidateId());
		Optional<HrmRecruitInterview> hrmInterview = lambdaQuery().eq(HrmRecruitInterview::getCandidateId, hrmRecruitInterview.getCandidateId()).orderByDesc(HrmRecruitInterview::getCreateTime).last("limit 1").oneOpt();
		if (hrmInterview.isPresent()) {
			if (hrmRecruitCandidate.getStatus().equals(CandidateStatusEnum.ARRANGE_AN_INTERVIEW.getValue())) {
				if (hrmInterview.get().getResult().equals(RecruitEnum.RecruitInterviewResult.CANCEL.getValue())) {
					operateType = 3;
				}
				hrmRecruitInterview.setInterviewId(hrmInterview.get().getInterviewId());
			}
		}
		hrmRecruitInterview.setOtherInterviewEmployeeIds(TagUtil.fromSet(setRecruitInterviewBO.getOtherInterviewEmployeeIds()));
		List<Integer> employeeIds = new ArrayList<>();
		if (setRecruitInterviewBO.getInterviewEmployeeId() != null) {
			employeeIds.add(setRecruitInterviewBO.getInterviewEmployeeId());
		}
		if (CollectionUtil.isNotEmpty(setRecruitInterviewBO.getOtherInterviewEmployeeIds())) {
			employeeIds.addAll(setRecruitInterviewBO.getOtherInterviewEmployeeIds());
		}
		if (hrmRecruitInterview.getInterviewId() != null) {
			candidateActionRecordService.updateInterviewRecord(hrmRecruitInterview, operateType);
			if (operateType == 3) {
				hrmRecruitInterview.setResult(1);
			}
			updateById(hrmRecruitInterview);
		} else {
			Optional<HrmRecruitInterview> maxNumOpt = lambdaQuery().eq(HrmRecruitInterview::getCandidateId, hrmRecruitInterview.getCandidateId()).orderByDesc(HrmRecruitInterview::getCreateTime).last("limit 1").oneOpt();
			Integer stageNum;
			if (maxNumOpt.isPresent()) {
				HrmRecruitInterview maxNum = maxNumOpt.get();
				if (maxNum.getResult() == RecruitEnum.RecruitInterviewResult.UNFINISHED.getValue()) {
					stageNum = maxNum.getStageNum() + 1;
				} else {
					stageNum = 1;
				}
			} else {
				stageNum = 1;
			}
			hrmRecruitInterview.setStageNum(stageNum);
			save(hrmRecruitInterview);
			candidateActionRecordService.addInterviewRecord(hrmRecruitInterview);
			HrmRecruitCandidate candidate = new HrmRecruitCandidate();
			candidate.setStatus(CandidateStatusEnum.ARRANGE_AN_INTERVIEW.getValue());
			candidate.setStatusUpdateTime(new Date());
			candidate.setCandidateId(hrmRecruitInterview.getCandidateId());
			candidate.setStageNum(stageNum);
			recruitCandidateService.updateById(candidate);
			HrmRecruitCandidate recruitCandidate = recruitCandidateService.getById(hrmRecruitInterview.getCandidateId());
			employeeIds.forEach(employeeId -> {
				//Send the interview arrangement notification to the interviewer
				AdminMessage adminMessage = new AdminMessage();
				adminMessage.setCreateUser(UserUtil.getUserId());
				adminMessage.setCreateTime(DateUtil.formatDateTime(new Date()));
				adminMessage.setRecipientUser(EmployeeCacheUtil.getUserId(employeeId));
				adminMessage.setLabel(8);
				adminMessage.setTypeId(hrmRecruitInterview.getCandidateId());
				adminMessage.setType(AdminMessageEnum.HRM_INTERVIEW_ARRANGEMENTS.getType());
				adminMessage.setTitle(recruitCandidate.getCandidateName());
				Integer type = setRecruitInterviewBO.getType() != null ? setRecruitInterviewBO.getType() : 0;
				adminMessage.setContent(MessageFormat.format("Scheduled {0} interview ({1} round):{2} {3}", InterviewType.parseName(type), stageNum, DateUtil.dayOfWeekEnum(DateUtil.date()).toChinese(), DateUtil.format(setRecruitInterviewBO.getInterviewTime(), "MM-dd HH:mm")));
				ApplicationContextHolder.getBean(AdminMessageService.class).save(adminMessage);
			});
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setInterviewResult(SetInterviewResultBO setInterviewResultBO) {
		HrmRecruitInterview hrmRecruitInterview = BeanUtil.copyProperties(setInterviewResultBO, HrmRecruitInterview.class);
		HrmRecruitCandidate hrmRecruitCandidate = recruitCandidateService.getById(setInterviewResultBO.getCandidateId());
		Optional<HrmRecruitInterview> hrmInterview = lambdaQuery().eq(HrmRecruitInterview::getCandidateId, hrmRecruitCandidate.getCandidateId()).
				eq(HrmRecruitInterview::getStageNum, hrmRecruitCandidate.getStageNum()).orderByDesc(HrmRecruitInterview::getCreateTime).last("limit 1").oneOpt();
		if (hrmInterview.isPresent()) {
			if (setInterviewResultBO.getResult().equals(hrmInterview.get().getResult())) {
				throw new CrmException(HrmCodeEnum.NO_INTERVIEW_ARRANGEMENT);
			}
			hrmRecruitInterview.setInterviewId(hrmInterview.get().getInterviewId());
			hrmRecruitInterview.setType(hrmInterview.get().getType());
			hrmRecruitInterview.setInterviewEmployeeId(hrmInterview.get().getInterviewEmployeeId());
			hrmRecruitInterview.setOtherInterviewEmployeeIds(hrmInterview.get().getOtherInterviewEmployeeIds());
		}
		updateById(hrmRecruitInterview);
		HrmRecruitCandidate candidate = new HrmRecruitCandidate();
		if (setInterviewResultBO.getResult().equals(RecruitEnum.RecruitInterviewResult.PASS.getValue())) {
			candidate.setStatus(CandidateStatusEnum.PASS_THE_INTERVIEW.getValue());
		} else if (setInterviewResultBO.getResult().equals(RecruitEnum.RecruitInterviewResult.NOT_PASS.getValue())) {
			candidate.setStatus(CandidateStatusEnum.OBSOLETE.getValue());
		} else if (setInterviewResultBO.getResult().equals(RecruitEnum.RecruitInterviewResult.CANCEL.getValue())) {
			HrmRecruitInterview interview = getById(hrmRecruitInterview.getInterviewId());
			candidateActionRecordService.cancelInterviewRecord(interview);
		}
		candidate.setStatusUpdateTime(new Date());
		candidate.setCandidateId(setInterviewResultBO.getCandidateId());
		recruitCandidateService.updateById(candidate);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addBatchInterview(SetRecruitInterviewBO setRecruitInterviewBO) {
		List<Integer> candidateIds = new ArrayList<>();
		if (null == setRecruitInterviewBO.getCandidateIds()) {
			candidateIds.add(setRecruitInterviewBO.getCandidateId());
			setRecruitInterviewBO.setCandidateIds(candidateIds);
		}
		setRecruitInterviewBO.getCandidateIds().forEach(candidateId -> {
			setRecruitInterviewBO.setCandidateId(candidateId);
			setInterview(setRecruitInterviewBO);
		});
	}
}
