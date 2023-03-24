package com.megazone.hrm.service.actionrecord.impl;

import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.hrm.constant.HrmActionBehaviorEnum;
import com.megazone.hrm.constant.HrmActionTypeEnum;
import com.megazone.hrm.entity.BO.UpdateScheduleBO;
import com.megazone.hrm.entity.PO.HrmAchievementEmployeeSeg;
import com.megazone.hrm.service.actionrecord.AbstractHrmActionRecordService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("employeeAppraisalActionRecordService")
@SysLog(subModel = SubModelType.HRM_APPRAISAL)
public class EmployeeAppraisalActionRecordServiceImpl extends AbstractHrmActionRecordService {

	private static HrmActionTypeEnum actionTypeEnum = HrmActionTypeEnum.EMPLOYEE_APPRAISAL;
	private static HrmActionBehaviorEnum behaviorEnum = HrmActionBehaviorEnum.UPDATE;


	/**
	 * Open the examination record
	 */
	@SysLogHandler(isReturn = true)
	public Content startAppraisalRecord(String appraisalName, List<Integer> employeeAppraisalIds) {
		String content = "Assessment enabled";
		employeeAppraisalIds.forEach(employeeAppraisalId -> {
			save(content, employeeAppraisalId);
		});
		return new Content(appraisalName, content + ":" + appraisalName, BehaviorEnum.UPDATE);
	}

	/**
	 * Submit for assessment
	 */
	@SysLogHandler(isReturn = true)
	public Content submitAppraisalRecord(Integer employeeAppraisalId) {
		String content = "Submitted target";
		save(content, employeeAppraisalId);
		return new Content("", content, BehaviorEnum.UPDATE);
	}

	/**
	 * Confirm the target
	 */
	@SysLogHandler(isReturn = true)
	public Content confirmAppraisalRecord(Integer employeeAppraisalId) {
		String content = "Target confirmed";
		save(content, employeeAppraisalId);
		return new Content("", content, BehaviorEnum.UPDATE);
	}

	/**
	 * Submit assessment
	 */
	@SysLogHandler(isReturn = true)
	public Content submitEvaluatoRecord(Integer employeeAppraisalId) {
		String content = "Submitted rating";
		save(content, employeeAppraisalId);
		return new Content("", content, BehaviorEnum.UPDATE);
	}

	/**
	 * Confirm the assessment results, the assessment is completed
	 */
	@SysLogHandler(isReturn = true)
	public Content confirmResult(String appraisalName, List<Integer> employeeAppraisalIds, boolean isLast) {
		String content;
		if (isLast) {
			content = "Confirm the assessment results, the assessment is completed";
		} else {
			content = "Confirm assessment results";
		}
		employeeAppraisalIds.forEach(employeeAppraisalId -> {
			save(content, employeeAppraisalId);
		});
		return new Content(appraisalName, content, BehaviorEnum.UPDATE);
	}

	/**
	 * Modify goal progress
	 */
	@SysLogHandler(isReturn = true)
	public Content updateSchedule(String appraisalName, UpdateScheduleBO updateScheduleBO, HrmAchievementEmployeeSeg seg) {
		String content = "Modified target" + seg.getSegName() + "The progress is " + updateScheduleBO.getSchedule() + "%, completion description:" + updateScheduleBO.getExplainDesc();
		save(content, seg.getEmployeeAppraisalId());
		return new Content(appraisalName, content, BehaviorEnum.UPDATE);
	}

	/**
	 * reject
	 *
	 * @param appraisalName
	 * @param status        2 rejection objective 3 rejection assessment
	 * @param reason        reason
	 */
	@SysLogHandler(isReturn = true)
	public Content reject(String appraisalName, Integer employeeAppraisalId, Integer status, String reason) {
		String content;
		if (status == 2) {
			content = "Objective rejected, reason for rejection: " + reason;
		} else {
			content = "Rejected the assessment, the reason for rejection: " + reason;
		}
		save(content, employeeAppraisalId);

		return new Content(appraisalName, content, BehaviorEnum.UPDATE);
	}

	/**
	 * Terminate the assessment
	 */
	@SysLogHandler(isReturn = true)
	public Content stopAppraisal(String appraisalName, List<Integer> employeeAppraisalIds) {
		String content = "Assessment terminated";
		employeeAppraisalIds.forEach(employeeAppraisalId -> {
			save(content, employeeAppraisalId);
		});
		return new Content(appraisalName, content, BehaviorEnum.UPDATE);
	}


	/**
	 * Save operation records
	 *
	 * @param content
	 * @param employeeAppraisalId
	 */
	private void save(String content, Integer employeeAppraisalId) {
		actionRecordService.saveRecord(actionTypeEnum, behaviorEnum, Collections.singletonList(content), employeeAppraisalId);
	}

}
