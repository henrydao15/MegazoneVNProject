package com.megazone.hrm.service.actionrecord.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.hrm.constant.*;
import com.megazone.hrm.entity.BO.DeleteLeaveInformationBO;
import com.megazone.hrm.entity.BO.UpdateInformationBO;
import com.megazone.hrm.entity.PO.*;
import com.megazone.hrm.entity.VO.HrmModelFiledVO;
import com.megazone.hrm.service.IHrmDeptService;
import com.megazone.hrm.service.IHrmEmployeeService;
import com.megazone.hrm.service.IHrmInsuranceSchemeService;
import com.megazone.hrm.service.IHrmRecruitChannelService;
import com.megazone.hrm.service.actionrecord.AbstractHrmActionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Employee operation records
 *
 * @author
 */
@Service("employeeActionRecordService")
@SysLog(subModel = SubModelType.HRM_EMPLOYEE)
public class EmployeeActionRecordServiceImpl extends AbstractHrmActionRecordService {


	private static HrmActionTypeEnum actionTypeEnum = HrmActionTypeEnum.EMPLOYEE;
	/**
	 * attribute kv
	 */
	private static Map<LabelGroupEnum, Dict> propertiesMap = new HashMap<>();

	static {
		propertiesMap.put(LabelGroupEnum.EDUCATIONAL_EXPERIENCE, Dict.create().set("education", "education").set("graduateSchool", "graduate school").set("major", "major").set("admissionTime", "Admission Time").set("graduationTime", "Graduation Time").set("teachingMethods", "Teaching Methods").set("isFirstDegree", "Whether the first degree"));
		propertiesMap.put(LabelGroupEnum.WORK_EXPERIENCE, Dict.create().set("workUnit", "workunit").set("post", "job").set("workStartTime", "workstart time").set("workEndTime", "Work End Time").set("leavingReason", "Reason for Resignation").set("witness", "Certifier").set("witnessPhone", "Certifier's mobile phone number").set("workRemarks", "Work Remarks"));
		propertiesMap.put(LabelGroupEnum.CERTIFICATE, Dict.create().set("certificateName", "certificate name").set("certificateLevel", "certificate level").set("certificateNum", "certificate number").set("startTime", "effective start date").set("endTime", "effective end date").set("issuingAuthority", "issuing authority").set("issuingTime", "issuing date").set("remarks", "Certificate Remarks"));
		propertiesMap.put(LabelGroupEnum.TRAINING_EXPERIENCE, Dict.create().set("trainingCourse", "Training Course").set("trainingOrganName", "Training OrganName").set("startTime", "Training Start Time").set("endTime", "Training End Time").set("trainingDuration", "Training Duration").set("trainingResults", "Training Results").set("trainingCertificateName", "Training Course Name").set("remarks", "training remarks"));
		propertiesMap.put(LabelGroupEnum.QUIT, Dict.create().set("planQuitTime", "planned quit date").set("applyQuitTime", "application quit date").set("salarySettlementTime", "salary settlement date ").set("quitType", "Resignation Type").set("quitReason", "Reason for Resignation").set("remarks", "Remarks"));
		propertiesMap.put(LabelGroupEnum.SALARY_CARD, Dict.create().set("salaryCardNum", "Salary Card Number").set("accountOpeningCity", "Account Opening City").set("bankName", "Bank Name").set("openingBank", "Payroll Card Opening Bank"));
		propertiesMap.put(LabelGroupEnum.SOCIAL_SECURITY, Dict.create().set("isFirstSocialSecurity", "Whether to pay social security for the first time").set("isFirstAccumulationFund", "Whether to pay the provident fund for the first time").set("socialSecurityNum", "Social Security Number").set("accumulationFundNum", "Provide Fund Account").set("socialSecurityStartMonth", "Enrollment Start Month").set("schemeId", "Enrollment Plan"));
		propertiesMap.put(LabelGroupEnum.CONTRACT, Dict.create().set("contractNum", "Contract Number").set("contractType", "Contract Type").set("startTime", "Contract Start Date").set("endTime", "Contract End Date").set("term", "Term").set("status", "Contract Status").set("signCompany", "Signing Company").set("signTime", "Contract signing date").set("remarks", "Remarks").set("isExpireRemind", "Whether it expires reminder"));
		propertiesMap.put(LabelGroupEnum.PERSONAL, Dict.create().set("employeeName", "name").set("mobile", "mobile").set("country", "country").set("nation", "nation").set("idType", "document type").set("idNumber", "document number").set("sex", "gender").set("dateOfBirth", "Birth Date").set("birthdayType", "Birthday Type").set("birthday", "Birthday").set("age", "Age").set("nativePlace", "NativePlace").set("address", "Location of household registration").set("highestEducation", "Highest education"));
		propertiesMap.put(LabelGroupEnum.COMMUNICATION, Dict.create().set("email", "mailbox"));
		propertiesMap.put(LabelGroupEnum.POST, Dict.create().set("jobNumber", "job number").set("entryTime", "entry time").set("deptId", "department").set("post", "post").set("parentId", "direct superior").set("postLevel", "rank").set("workCity", "work city").set("workAddress", "workplace").set("workDetailAddress", "detailed worksite").set("employmentForms", "employment form").set("probation", "probation period").set("becomeTime", "Return dayperiod").set("companyAgeStartTime", "Age start date").set("companyAge", "Age").set("channelId", "Recruitment channel").set("status", "Employee state"));
		propertiesMap.put(LabelGroupEnum.CONTACT_PERSON, Dict.create().set("", "contact name").set("relation", "relation").set("contactsPhone", "contactsPhone").set("contactsWorkUnit", "contacts work unit").set("contactsPost", "contacts post").set("contactsAddress", "contacts address"));
	}

	@Autowired
	private IHrmEmployeeService employeeService;
	@Autowired
	private IHrmDeptService deptService;
	@Autowired
	private IHrmInsuranceSchemeService insuranceSchemeService;
	private List<String> textList = new ArrayList<>();

	public static Object parseValue(Object value, Integer type, boolean isNeedStr) {
		if (ObjectUtil.isEmpty(value) && !type.equals(FieldEnum.DETAIL_TABLE.getType())) {
			return isNeedStr ? "null" : "";
		}
		FieldEnum fieldEnum = FieldEnum.parse(type);
		switch (fieldEnum) {
			case BOOLEAN_VALUE: {
				return "1".equals(value) ? "Yes" : "No";
			}
			case AREA_POSITION: {
				StringBuilder stringBuilder = new StringBuilder();
				if (value instanceof CharSequence) {
					value = JSON.parseArray(value.toString());
				}
				for (Map<String, Object> map : ((List<Map<String, Object>>) value)) {
					stringBuilder.append(map.get("name")).append(" ");
				}
				return stringBuilder.toString();
			}
			case CURRENT_POSITION: {
				if (value instanceof CharSequence) {
					value = JSON.parseObject(value.toString());
				}
				return Optional.ofNullable(((Map<String, Object>) value).get("address")).orElse("").toString();
			}
			case DATE_INTERVAL: {
				if (value instanceof Collection) {
					value = StrUtil.join(Const.SEPARATOR, value);
				}
				return isNeedStr ? value.toString() : value;
			}
			case SINGLE_USER:
			case USER:
				List<String> ids = StrUtil.splitTrim(value.toString(), Const.SEPARATOR);
				return ids.stream().map(id -> UserCacheUtil.getUserName(Long.valueOf(id))).collect(Collectors.joining(Const.SEPARATOR));
			case STRUCTURE:
				List<String> deptIds = StrUtil.splitTrim(value.toString(), Const.SEPARATOR);
				return deptIds.stream().map(id -> UserCacheUtil.getDeptName(Integer.valueOf(id))).collect(Collectors.joining(Const.SEPARATOR));
			case DETAIL_TABLE: {
				if (value == null) {
					value = new ArrayList<>();
				}
				if (value instanceof String) {
					if ("".equals(value)) {
						value = new ArrayList<>();
					} else {
						value = JSON.parseArray((String) value);
					}
				}
				List<Map<String, Object>> list = new ArrayList<>();
				JSONArray array = new JSONArray((List<Object>) value);
				for (int i = 0; i < array.size(); i++) {
					JSONArray objects = array.getJSONArray(i);
					for (int j = 0; j < objects.size(); j++) {
						Map<String, Object> map = new HashMap<>();
						JSONObject data = objects.getJSONObject(j);
						map.put("name", data.get("name"));
						map.put("value", parseValue(data.get("value"), data.getInteger("type"), false));
						list.add(map);
					}
				}
				return list;
			}
			default:
				return isNeedStr ? value.toString() : value;
		}

	}

	/**
	 * Detailed table replacement
	 *
	 * @param oldFieldValue
	 * @param newFieldValue
	 * @param name
	 * @param type
	 * @param textList
	 */
	public static void parseDetailTable(Object oldFieldValue, Object newFieldValue, String name, Integer type, List<String> textList) {
		List<Map<String, Object>> oldFieldValues = (List<Map<String, Object>>) parseValue(oldFieldValue, type, false);
		List<Map<String, Object>> newFieldValues = (List<Map<String, Object>>) parseValue(newFieldValue, type, false);
		int size = oldFieldValues.size() >= newFieldValues.size() ? oldFieldValues.size() : newFieldValues.size();
		for (int i = 0; i < size; i++) {
			Object oldValue = oldFieldValues.size() > i ? oldFieldValues.get(i).get("value") : "";
			Object newValue = newFieldValues.size() > i ? newFieldValues.get(i).get("value") : "";
			if (!Objects.equals(oldValue, newValue)) {
				Map<String, Object> map = oldFieldValues.size() >= newFieldValues.size() ? oldFieldValues.get(i) : newFieldValues.get(i);
				textList.add("Modify " + name + " " + map.get("name") + " from " + oldValue + " to " + newValue + ".");
			}
		}
	}

	/**
	 * Create/delete operation records
	 */
	@SysLogHandler(isReturn = true)
	public Content addOrDeleteRecord(HrmActionBehaviorEnum behaviorEnum, LabelGroupEnum labelGroupEnum, Integer employeeId) {
		String content = behaviorEnum.getName() + "le" + labelGroupEnum.getDesc();
		actionRecordService.saveRecord(actionTypeEnum, behaviorEnum, Collections.singletonList(content), employeeId);
		HrmEmployee employee = employeeService.getById(employeeId);
		if (HrmActionBehaviorEnum.ADD.equals(behaviorEnum)) {
			return new Content(employee.getEmployeeName(), content, BehaviorEnum.SAVE);
		} else {
			return new Content(employee.getEmployeeName(), content, BehaviorEnum.DELETE);
		}
	}

	/**
	 * Employee attachment attachment
	 *
	 * @param employeeFile
	 */
	@SysLogHandler(isReturn = true)
	public Content addFileRecord(HrmEmployeeFile employeeFile, HrmActionBehaviorEnum behaviorEnum) {
		String content;
		if (behaviorEnum.equals(HrmActionBehaviorEnum.ADD)) {
			content = "Uploaded" + EmployeeFileType.parseName(employeeFile.getSubType()) + "Attachment";
		} else {
			content = behaviorEnum.getName() + "" + EmployeeFileType.parseName(employeeFile.getSubType()) + "Attachment";
		}
		actionRecordService.saveRecord(actionTypeEnum, behaviorEnum, Collections.singletonList(content), employeeFile.getEmployeeId());
		HrmEmployee employee = employeeService.getById(employeeFile.getEmployeeId());
		return new Content(employee.getEmployeeName(), content, BehaviorEnum.SAVE);
	}

	/**
	 * Staff transfer/transfer/promotion
	 */
	@SysLogHandler(isReturn = true)
	public Content changeRecord(HrmEmployeeChangeRecord changeRecord) {
		HrmEmployee employee = employeeService.getById(changeRecord.getEmployeeId());
		StringBuilder content = new StringBuilder();
		HrmActionBehaviorEnum changeTypeEnum = HrmActionBehaviorEnum.parse(changeRecord.getChangeType());
		content.append("as").append(employee.getEmployeeName());
		switch (changeTypeEnum) {
			case CHANGE_POST:
			case PROMOTED:
			case DEGRADE:
			case CHANGE_FULL_TIME_EMPLOYEE:
				content
						.append("Added a personnel change [").append(changeTypeEnum.getName()).append("], change reason:")
						.append(ChangeReasonEnum.parseName(changeRecord.getChangeReason()))
						.append(", the effective date is: ").append(DateUtil.formatDate(changeRecord.getEffectTime()));
				String oldDeptValue = "None";
				String newDeptValue = "None";
				if (changeRecord.getOldDept() != null) {
					oldDeptValue = deptService.getById(changeRecord.getOldDept()).getName();
				}
				if (changeRecord.getNewDept() != null) {
					newDeptValue = deptService.getById(changeRecord.getNewDept()).getName();
				}
				if (!oldDeptValue.equals(newDeptValue)) {
					content.append(", the department is changed from ").append(oldDeptValue).append("changed to ").append(newDeptValue);
				}
				if (changeRecord.getOldPost() == null) {
					changeRecord.setOldPost("none");
				}
				if (!changeRecord.getOldPost().equals(changeRecord.getNewPostLevel())) {
					content.append(", post by ").append(changeRecord.getOldPost()).append("change to").append(changeRecord.getNewPost());
				}
				if (changeRecord.getOldPostLevel() == null) {
					changeRecord.setOldPostLevel("none");
				}
				if (!changeRecord.getOldPostLevel().equals(changeRecord.getNewPostLevel())) {
					content.append(", the rank is changed from ").append(changeRecord.getOldPostLevel()).append("change to").append(changeRecord.getNewPostLevel());
				}
				if (changeRecord.getOldWorkAddress() == null) {
					changeRecord.setOldWorkAddress("none");
				}
				if (!changeRecord.getOldWorkAddress().equals(changeRecord.getNewWorkAddress())) {
					content.append(", the work address is changed from ").append(changeRecord.getOldWorkAddress()).append("changed to").append(changeRecord.getNewWorkAddress());
				}
				if (changeTypeEnum.equals(HrmActionBehaviorEnum.CHANGE_FULL_TIME_EMPLOYEE)) {
					content.append(", trial period: ").append(changeRecord.getProbation()).append("month");
				}
				break;
			case BECOME:
				content
						.append("Regularized, the date of positive transfer").append(DateUtil.formatDate(changeRecord.getEffectTime()));
				break;
			default:
				break;
		}
		if (StrUtil.isNotEmpty(changeRecord.getRemarks())) {
			content.append(",Remarks:").append(changeRecord.getRemarks());
		}
		actionRecordService.saveRecord(actionTypeEnum, changeTypeEnum, Collections.singletonList(content.toString()), changeRecord.getEmployeeId());
		return new Content(employee.getEmployeeName(), content.toString(), BehaviorEnum.UPDATE);
	}

	/**
	 * Resignation operation record
	 *
	 * @param quitInfo
	 */
	@SysLogHandler(isReturn = true)
	public Content quitRecord(HrmEmployeeQuitInfo quitInfo) {
		HrmEmployee employee = employeeService.getById(quitInfo.getEmployeeId());
		StringBuilder content = new StringBuilder();
		content
				.append("as").append(employee.getEmployeeName());
		if (quitInfo.getQuitReason() == null) {
			content.append("Retired");
		} else {
			QuitReasonEnum reasonEnum = QuitReasonEnum.parse(quitInfo.getQuitReason());
			content.append("Added a resignation application, Yu").append(DateUtil.formatDate(quitInfo.getApplyQuitTime()))
					.append("Proposed resignation, planned resignation date: ").append(DateUtil.formatDate(quitInfo.getPlanQuitTime()))
					.append("Reason: ").append(reasonEnum.getQuitType().getName()).append("-").append(reasonEnum.getName());
		}
		if (StrUtil.isNotEmpty(quitInfo.getRemarks())) {
			content.append(",Remarks:").append(quitInfo.getRemarks());
		}
		actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.QUIT, Collections.singletonList(content.toString()), quitInfo.getEmployeeId());
		return new Content(employee.getEmployeeName(), content.toString(), BehaviorEnum.UPDATE);
	}

	/**
	 * Other tables of employee personal information are updated, see labelGroupEnum for details
	 *
	 * @param labelGroupEnum
	 * @param oldRecord
	 * @param newRecord
	 * @param employeeId
	 */
	@SysLogHandler(isReturn = true)
	public Content entityUpdateRecord(LabelGroupEnum labelGroupEnum, Map<String, Object> oldRecord, Map<String, Object> newRecord, Integer employeeId) {
		Dict properties = propertiesMap.get(labelGroupEnum);
		List<String> contentList = entityCommonUpdateRecord(labelGroupEnum, properties, oldRecord, newRecord);
		actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, contentList, employeeId);
		HrmEmployee employee = employeeService.getById(employeeId);
		return new Content(employee.getEmployeeName(), CollUtil.join(contentList, ","), BehaviorEnum.UPDATE);
	}

	/**
	 * Modify the insurance plan
	 */
	@SysLogHandler(isReturn = true)
	public Content updateSchemeRecord(HrmInsuranceScheme oldInsuranceScheme, HrmInsuranceScheme newInsuranceScheme, HrmEmployee employee) {
		String oldSchemeName;
		String newSchemeName;
		if (oldInsuranceScheme == null) {
			oldSchemeName = "null";
		} else {
			oldSchemeName = oldInsuranceScheme.getSchemeName();
		}
		if (newInsuranceScheme == null) {
			newSchemeName = "null";
		} else {
			newSchemeName = newInsuranceScheme.getSchemeName();
		}
		String content =
				"is" + employee.getEmployeeName() +
						"Modified the insurance plan, by【" +
						oldSchemeName + "] is changed to [" +
						newSchemeName + "]";
		actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, Collections.singletonList(content), employee.getEmployeeId());
		return new Content(employee.getEmployeeName(), content, BehaviorEnum.UPDATE);
	}

	@Override
	protected String compare(LabelGroupEnum labelGroupEnum, Dict properties, String newFieldKey, String oldValue, String newValue) {
		String content = super.compare(labelGroupEnum, properties, newFieldKey, oldValue, newValue);
		switch (labelGroupEnum) {
			case EDUCATIONAL_EXPERIENCE:
				if ("education".equals(newFieldKey)) {
					if (!"null".equals(oldValue)) {
						oldValue = EmployeeEducationEnum.parseName(Integer.parseInt(oldValue));
					}
					if (!"null".equals(newValue)) {
						newValue = EmployeeEducationEnum.parseName(Integer.parseInt(newValue));
					}
					content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
				} else if ("teachingMethods".equals(newFieldKey)) {
					if (!"null".equals(oldValue)) {
						oldValue = TeachingMethodsEnum.parseName(Integer.parseInt(oldValue));
					}
					if (!"null".equals(newValue)) {
						newValue = TeachingMethodsEnum.parseName(Integer.parseInt(newValue));
					}
					content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
				} else if ("isFirstDegree".equals(newFieldKey)) {
					if (!"null".equals(oldValue)) {
						oldValue = IsEnum.parseName(Integer.parseInt(oldValue));
					}
					if (!"null".equals(newValue)) {
						newValue = IsEnum.parseName(Integer.parseInt(newValue));
					}
					content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
				}
				break;
			case QUIT:
				if ("quitType".equals(newFieldKey)) {
					if (!"null".equals(oldValue)) {
						oldValue = QuitTypeEnum.parseName(Integer.parseInt(oldValue));
					}
					if (!"null".equals(newValue)) {
						newValue = QuitTypeEnum.parseName(Integer.parseInt(newValue));
					}
					content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
				} else if ("quitReason".equals(newFieldKey)) {
					if (!"null".equals(oldValue)) {
						oldValue = QuitReasonEnum.parseName(Integer.parseInt(oldValue));
					}
					if (!"null".equals(newValue)) {
						newValue = QuitReasonEnum.parseName(Integer.parseInt(newValue));
					}
					content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
				}
				break;
			case SOCIAL_SECURITY:
				if ("isFirstSocialSecurity".equals(newFieldKey)) {
					if (!"null".equals(oldValue)) {
						oldValue = IsEnum.parseName(Integer.parseInt(oldValue));
					}
					if (!"null".equals(newValue)) {
						newValue = IsEnum.parseName(Integer.parseInt(newValue));
					}
					content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
				} else if ("isFirstAccumulationFund".equals(newFieldKey)) {
					if (!"null".equals(oldValue)) {
						oldValue = IsEnum.parseName(Integer.parseInt(oldValue));
					}
					if (!"null".equals(newValue)) {
						newValue = IsEnum.parseName(Integer.parseInt(newValue));
					}
					content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
				} else if ("schemeId".equals(newFieldKey)) {
					HrmInsuranceScheme oldScheme = insuranceSchemeService.getById(oldValue);
					HrmInsuranceScheme newScheme = insuranceSchemeService.getById(newValue);
					if (oldScheme != null) {
						oldValue = oldScheme.getSchemeName();
					}
					if (newScheme != null) {
						newValue = newScheme.getSchemeName();
					}
					content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
				}
				break;
			case CONTRACT:
				if ("contractType".equals(newFieldKey)) {
					if (!"null".equals(oldValue)) {
						oldValue = EmployeeContractType.parseName(Integer.parseInt(oldValue));
					}
					if (!"null".equals(newValue)) {
						newValue = EmployeeContractType.parseName(Integer.parseInt(newValue));
					}
					content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
				} else if ("status".equals(newFieldKey)) {
					if (!"null".equals(oldValue)) {
						oldValue = EmployeeContractStatus.parseName(Integer.parseInt(oldValue));
					}
					if (!"null".equals(newValue)) {
						newValue = EmployeeContractStatus.parseName(Integer.parseInt(newValue));
					}
					content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
				} else if ("isExpireRemind".equals(newFieldKey)) {
					if (!"null".equals(oldValue)) {
						oldValue = IsEnum.parseName(Integer.parseInt(oldValue));
					}
					if (!"null".equals(newValue)) {
						newValue = IsEnum.parseName(Integer.parseInt(newValue));
					}
					content = "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
				}
				break;
			default:
				break;
		}
		return content;
	}

	@SysLogHandler(isReturn = true)
	public Content cancelLeave(DeleteLeaveInformationBO deleteLeaveInformationBO) {
		StringBuilder content = new StringBuilder();
		content.append("Cancelled resignation");
		if (StrUtil.isNotEmpty(deleteLeaveInformationBO.getRemarks())) {
			content.append(",reason:").append(deleteLeaveInformationBO.getRemarks());
		}
		actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, Collections.singletonList(content.toString()), deleteLeaveInformationBO.getEmployeeId());
		HrmEmployee employee = employeeService.getById(deleteLeaveInformationBO.getEmployeeId());
		return new Content(employee.getEmployeeName(), content.toString(), BehaviorEnum.UPDATE);
	}

	/**
	 * Save fixed field records
	 *
	 * @param oldObj
	 * @param newObj
	 * @param labelGroupEnum
	 * @param employeeId
	 */
	@SysLogHandler(isReturn = true)
	public Content employeeFixedFieldRecord(Map<String, Object> oldObj, Map<String, Object> newObj, LabelGroupEnum labelGroupEnum, Integer employeeId) {
		HrmEmployee employee = employeeService.getById(employeeId);
		try {
			textList.clear();
			searchChange(textList, oldObj, newObj, labelGroupEnum);
			if (textList.size() > 0) {
				actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, textList, employeeId);
				return new Content(employee.getEmployeeName(), CollUtil.join(textList, ","), BehaviorEnum.UPDATE);
			}

		} finally {
			textList.clear();
		}
		return new Content(employee.getEmployeeName(), "", BehaviorEnum.UPDATE);
	}

	/**
	 * Save non-fixed field records
	 *
	 * @param newFieldList
	 * @param oldFieldList
	 */
	@SysLogHandler(isReturn = true)
	public Content employeeNOFixedFieldRecord(List<UpdateInformationBO.InformationFieldBO> newFieldList, List<HrmModelFiledVO> oldFieldList, Integer employeeId) {
		HrmEmployee employee = employeeService.getById(employeeId);
		textList.clear();
		if (newFieldList == null) {
			return new Content(employee.getEmployeeName(), "", BehaviorEnum.UPDATE);
		}
		newFieldList.forEach(newField -> {
			for (HrmModelFiledVO oldField : oldFieldList) {
				if (oldField.getFieldId().equals(newField.getFieldId())) {
					if (ObjectUtil.isEmpty(oldField.getFieldValue()) && ObjectUtil.isEmpty(newField.getFieldValue())) {
						continue;
					}
					if (Objects.equals(FieldEnum.parse(oldField.getType()), FieldEnum.DETAIL_TABLE)) {
						parseDetailTable(oldField.getFieldValue(), newField.getFieldValue(), oldField.getName(), oldField.getType(), textList);
					} else {
						String oldFieldValue = (String) parseValue(oldField.getFieldValue(), oldField.getType(), true);
						String newFieldValue = (String) parseValue(newField.getFieldValue(), newField.getType(), true);
						if (!oldFieldValue.equals(newFieldValue)) {
							textList.add("Modify " + oldField.getName() + " from " + oldFieldValue + " to " + newFieldValue + ".");
						}
					}
				}
			}
		});
		if (textList.size() > 0) {
			actionRecordService.saveRecord(actionTypeEnum, HrmActionBehaviorEnum.UPDATE, textList, employeeId);
			return new Content(employee.getEmployeeName(), CollUtil.join(textList, ","), BehaviorEnum.UPDATE);
		}
		return new Content(employee.getEmployeeName(), "", BehaviorEnum.UPDATE);
	}

	/**
	 * Compare the changed fields
	 *
	 * @param textList different fields
	 * @param oldObj
	 * @param newObj
	 * @param hrmTypes type
	 */
	private void searchChange(List<String> textList, Map<String, Object> oldObj, Map<String, Object> newObj, LabelGroupEnum hrmTypes) {
		for (String oldKey : oldObj.keySet()) {
			for (String newKey : newObj.keySet()) {
				if (propertiesMap.get(hrmTypes).containsKey(oldKey)) {
					Object oldValue = oldObj.get(oldKey);
					Object newValue = newObj.get(newKey);
					if (oldValue instanceof Date) {
						oldValue = DateUtil.formatDateTime((Date) oldValue);
					}
					if (newValue instanceof Date) {
						newValue = DateUtil.formatDateTime((Date) newValue);
					}
					if (ObjectUtil.isEmpty(oldValue) || ("address".equals(oldKey))) {
						oldValue = "null";
					}
					if (ObjectUtil.isEmpty(newValue) || ("address".equals(newKey))) {
						newValue = "null";
					}
					if (oldValue instanceof BigDecimal || newValue instanceof BigDecimal) {
						oldValue = Convert.toBigDecimal(oldValue, new BigDecimal(0)).setScale(2, BigDecimal.ROUND_UP).toString();
						newValue = Convert.toBigDecimal(newValue, new BigDecimal(0)).setScale(2, BigDecimal.ROUND_UP).toString();
					}
					if (newKey.equals(oldKey) && !Objects.equals(oldValue, newValue)) {
						switch (oldKey) {
							case "idType":
								if (!"null".equals(newValue)) {
									newValue = IdTypeEnum.parseName(Integer.valueOf(newValue.toString()));
								}
								if (!"null".equals(oldValue)) {
									oldValue = IdTypeEnum.parseName(Integer.valueOf(oldValue.toString()));
								}
								break;
							case "sex":
								if (!"null".equals(newValue)) {
									newValue = newValue.equals(1) ? "male" : "female";
								}
								if (!"null".equals(oldValue)) {
									oldValue = oldValue.equals(1) ? "male" : "female";
								}
								break;
							case "probation":
								if (!"null".equals(newValue)) {
									newValue = newValue.equals(0) ? "No trial period" : newValue;
								}
								if (!"null".equals(oldValue)) {
									oldValue = oldValue.equals(0) ? "No trial period" : oldValue;
								}
								break;
							case "birthdayType":
								if (!"null".equals(newValue)) {
									newValue = newValue.equals(1) ? "Gregorian calendar" : "Lunar calendar";
								}
								if (!"null".equals(oldValue)) {
									oldValue = oldValue.equals(1) ? "Gregorian Calendar" : "Lunar Calendar";
								}
								break;
							case "employmentforms":
								if (!"null".equals(newValue)) {
									newValue = newValue.equals(1) ? "formal" : "informal";
								}
								if (!"null".equals(oldValue)) {
									oldValue = oldValue.equals(1) ? "formal" : "informal";
								}
								break;
							case "parentId":
								if (!"null".equals(newValue)) {
									HrmEmployee newHrmEmployee = ApplicationContextHolder.getBean(IHrmEmployeeService.class).getById(newValue.toString());
									if (null != newHrmEmployee) {
										newValue = newHrmEmployee.getEmployeeName();
									}
								}
								if (!"null".equals(oldValue)) {
									HrmEmployee oldHrmEmployee = ApplicationContextHolder.getBean(IHrmEmployeeService.class).getById(oldValue.toString());
									if (null != oldHrmEmployee) {
										oldValue = oldHrmEmployee.getEmployeeName();
									}
								}
								break;
							case "channelId":
								if (!"null".equals(newValue)) {
									HrmRecruitChannel newChannel = ApplicationContextHolder.getBean(IHrmRecruitChannelService.class).getById(newValue.toString());
									if (newChannel != null) {
										newValue = newChannel.getValue();
									}
								}
								if (!"null".equals(oldValue)) {
									HrmRecruitChannel oldChannel = ApplicationContextHolder.getBean(IHrmRecruitChannelService.class).getById(oldValue.toString());
									if (oldChannel != null) {
										oldValue = oldChannel.getValue();
									}
								}
								break;
							default:
								break;
						}
						if (ObjectUtil.isEmpty(oldValue)) {
							oldValue = "null";
						}
						if (ObjectUtil.isEmpty(newValue)) {
							newValue = "null";
						}
						textList.add("Modify " + propertiesMap.get(hrmTypes).get(oldKey) + " from " + oldValue + " to " + newValue + ".");
					}
				}
			}
		}
	}
}
