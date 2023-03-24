package com.megazone.hrm.service.actionrecord.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.hrm.constant.*;
import com.megazone.hrm.entity.BO.UpdateRecruitPostStatusBO;
import com.megazone.hrm.entity.PO.HrmRecruitPostType;
import com.megazone.hrm.service.IHrmRecruitPostTypeService;
import com.megazone.hrm.service.actionrecord.AbstractHrmActionRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HrmRecruitPostActionRecordService.java
 * @Description: Recruitment position operation record
 * @author: hmb
 * @date: 2020-04-29 16:14
 */
@Service("recruitPostActionRecordService")
@SysLog(subModel = SubModelType.HRM_RECRUITMENT)
public class RecruitPostActionRecordServiceImpl extends AbstractHrmActionRecordService {

	private static HrmActionTypeEnum actionTypeEnum = HrmActionTypeEnum.RECRUIT_POST;
	private static LabelGroupEnum labelGroupEnum = LabelGroupEnum.RECRUIT_POST;
	/**
	 * attribute kv
	 */
	private static Dict properties = Dict.create().set("postName", "PostName").set("deptId", "Department").set("employmentForms", "employmentForms").set("employmentStatus ", "Nature of work").set("city", "City of work")
			.set("recruitNum", "Number of Recruits").set("reason", "Reasons for Recruitment").set("workTime", "Work Experience").set("educationRequire", "Education Requirements").set("minSalary", "Start Salary")
			.set("maxSalary", "end salary").set("latestEntryTime", "latest entry time").set("ownerEmployeeId", "responsible person").set("description", "job description")
			.set("emergencyLevel", "Emergency Level").set("postTypeId", "PostType").set("minAge", "MinAge").set("maxAge", "MaxAge");
	@Autowired
	private IHrmRecruitPostTypeService postTypeService;

	/**
	 * Create/delete operation records
	 */
	@SysLogHandler(isReturn = true)
	public Content addOrDeleteRecord(HrmActionBehaviorEnum behaviorEnum, Integer postId, String object) {
		String content = "The " + behaviorEnum.getName() + StringUtils.SPACE + labelGroupEnum.getDesc();
		actionRecordService.saveRecord(actionTypeEnum, behaviorEnum, Collections.singletonList(content), postId);
		if (HrmActionBehaviorEnum.ADD.equals(behaviorEnum)) {
			return new Content(object, content, BehaviorEnum.SAVE);
		} else {
			return new Content(object, content, BehaviorEnum.DELETE);
		}
	}

	/**
	 * Modify job status operation record
	 */
	public void updateStatusRecord(UpdateRecruitPostStatusBO updateRecruitPostStatusBO) {
		String content = "";
		if (updateRecruitPostStatusBO.getStatus() == IsEnum.YES.getValue()) {
			content += "enable";
		} else {
			content += "stop";
		}
		content += "Position";
		if (StrUtil.isNotEmpty(updateRecruitPostStatusBO.getReason())) {
			content += ",reason:" + updateRecruitPostStatusBO.getReason();
		}
		actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, Collections.singletonList(content), updateRecruitPostStatusBO.getPostId());
	}

	/**
	 * Job entity class modification
	 */
	public void entityUpdateRecord(Map<String, Object> oldRecord, Map<String, Object> newRecord, Integer postId) {
		List<String> contentList = entityCommonUpdateRecord(labelGroupEnum, properties, oldRecord, newRecord);
		actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, contentList, postId);
	}

	@Override
	protected String compare(LabelGroupEnum labelGroupEnum, Dict properties, String newFieldKey, String oldValue, String newValue) {
		String content = super.compare(labelGroupEnum, properties, newFieldKey, oldValue, newValue);
		if ("deptId".equals(newFieldKey)) {
			content = hrmDeptCompare(properties.getStr(newFieldKey), oldValue, newValue);
		} else if ("workTime".equals(newFieldKey)) {
			if (!"null".equals(oldValue)) {
				oldValue = RecruitEnum.RecruitPostWorkTime.parseName(Integer.parseInt(oldValue));
			}
			if (!"null".equals(newValue)) {
				newValue = RecruitEnum.RecruitPostWorkTime.parseName(Integer.parseInt(newValue));
			}
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		} else if ("educationRequire".equals(newFieldKey)) {
			if (!"null".equals(oldValue)) {
				oldValue = PostEducationEnum.parseName(Integer.parseInt(oldValue));
			}
			if (!"null".equals(newValue)) {
				newValue = PostEducationEnum.parseName(Integer.parseInt(newValue));
			}
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		} else if ("ownerEmployeeId".equals(newFieldKey)) {
			content = employeeCompare(properties.getStr(newFieldKey), oldValue, newValue);
		} else if ("emergencyLevel".equals(newFieldKey)) {
			if (!"null".equals(oldValue)) {
				oldValue = RecruitEnum.RecruitPostEmergencyLevel.parseName(Integer.parseInt(oldValue));
			}
			if (!"null".equals(newValue)) {
				newValue = RecruitEnum.RecruitPostEmergencyLevel.parseName(Integer.parseInt(newValue));
			}
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		} else if ("employmentForms".equals(newFieldKey)) {
			if (!"null".equals(oldValue)) {
				oldValue = EmploymentFormsEnum.parseName(Integer.parseInt(oldValue));
			}
			if (!"null".equals(newValue)) {
				newValue = EmploymentFormsEnum.parseName(Integer.parseInt(newValue));
			}
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		} else if ("employmentStatus".equals(newFieldKey)) {
			if (!"null".equals(oldValue)) {
				oldValue = EmployeeStatusEnum.parseName(Integer.parseInt(oldValue));
			}
			if (!"null".equals(newValue)) {
				newValue = EmployeeStatusEnum.parseName(Integer.parseInt(newValue));
			}
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		} else if ("postTypeId".equals(newFieldKey)) {
			HrmRecruitPostType oldPoseType = postTypeService.getById(oldValue);
			HrmRecruitPostType newPoseType = postTypeService.getById(newValue);
			if (oldPoseType != null) {
				oldValue = oldPoseType.getName();
			}
			if (!"null".equals(newValue)) {
				newValue = newPoseType.getName();
			}
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		} else if ("minAge".equals(newFieldKey) || "maxAge".equals(newFieldKey)) {
			oldValue = "-1".equals(oldValue) ? "Unlimited" : oldValue;
			newValue = "-1".equals(newValue) ? "Unlimited" : newValue;
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		} else if ("minSalary".equals(newFieldKey) || "maxSalary".equals(newFieldKey)) {
			oldValue = "-1.00".equals(oldValue) ? "Negotiable" : oldValue;
			newValue = "-1.00".equals(newValue) ? "Negotiable" : newValue;
			content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
		}
		return content;
	}

}
