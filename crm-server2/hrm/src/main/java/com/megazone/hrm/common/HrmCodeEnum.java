package com.megazone.hrm.common;

import com.megazone.core.common.Const;
import com.megazone.core.common.ResultCode;

/**
 * @author Management background response error code enumeration class
 */

public enum HrmCodeEnum implements ResultCode {
	//HR error code
	IDENTITY_INFORMATION_IS_ILLEGAL(6001, "%s: ID card is illegal"),
	CUSTOM_FORM_NAME_CANNOT_BE_REPEATED(6002, "Custom form name cannot be repeated"),
	CUSTOM_FORM_NAME_DUPLICATES_SYSTEM_FIELD(6003, "[%s]The custom form name is duplicated with the system field, please use another field"),
	THE_EMPLOYEE_HAS_ALREADY_HANDLED_THE_RESIGNATION(6004, "The employee has already resigned"),
	SOCIAL_SECURITY_SCHEMES_ARE_USED_BY_EMPLOYEES(6005, "The social security scheme is used by employees and cannot be deleted"),
	CAN_ONLY_DELETE_PERFORMANCES_THAT_HAVE_NOT_BEEN_EVALUATED(6006, "Only delete performances that have not been evaluated or terminated"),
	YOU_ARE_NOT_THE_CURRENT_REVIEWER(6007, "You are not the current reviewer"),
	RESULTS_ASSESSMENT_NOT_COMPLETED(6008, "The result evaluation has not been completed, and the result cannot be confirmed"),
	CAN_ONLY_MODIFY_SALARY_AND_INCOME_TAX(6009, "Only modify salary income tax"),
	THERE_ARE_EMPLOYEES_UNDER_THE_DEPARTMENT(6010, "There are employees under the department, which cannot be deleted"),
	THIS_MONTH_S_SALARY_HAS_BEEN_GENERATED(6011, "This month's salary has been generated"),
	SOCIAL_SECURITY_DATA_IS_NOT_GENERATED_THIS_MONTH(6012, "Social security data is not generated"),
	JOB_NUMBER_EXISTED(6013, "%s job number already exists"),
	NO_INITIAL_CONFIGURATION(6014, "No initialization configuration"),
	FIELD_ALREADY_EXISTS(6015, "%s already exists"),
	PHONE_NUMBER_ALREADY_EXISTS(6016, "%s:The phone number already exists"),
	THERE_ARE_SUB_DEPARTMENTS_THAT_CANNOT_BE_DELETED(6017, "There are sub-departments under the department and cannot be deleted"),
	CAN_T_ARCHIVE(6018, "There are employees whose assessment status is not completed or terminated, please file them after processing"),
	LAST_RATING_IS_EMPTY(6019, "You are the first rater"),
	CAN_T_MODIFY_PROGRESS(6020, "The progress cannot be modified after receiving confirmation or termination"),
	SALARY_GROUP_NOT_CONFIG(6021, "The salary group is not configured"),
	INSURANCE_NOT_CONFIG(6022, "The social security scheme is not configured"),
	THE_SALARY_GROUP_ALREADY_HAS_SELECTED_EMPLOYEE(6023, "The selected employee already exists in the salary group"),
	THE_SALARY_GROUP_ALREADY_HAS_SELECTED_DEPT(6024, "The selected department already exists in the salary group"),
	SALARY_NEEDS_EXAMINE(6025, "The salary needs to be reviewed before adding next month's salary"),
	UNABLE_TO_MATCH_HRM_EMPLOYEES(6026, "Unable to match HR employees"),
	SALARY_CANNOT_BE_DELETED(6027, "Only one month's salary record can't be deleted"),
	ATTENDANCE_DATA_ERROR(6028, "Attendance data error"),
	INSURANCE_CANNOT_BE_DELETED(6029, "There is only one month of social security records and cannot be deleted"),
	CAN_T_PASS(6030, "Forced distribution has been turned on, and the pass cannot be confirmed beyond the specified ratio"),
	NO_EXAMINER(6031, "There is no examiner, please confirm whether to delete the examiner"),
	PARENT_DOES_NOT_EXIST(6032, "The superior of employee %s does not exist and cannot “%s”"),
	DEPT_DOES_NOT_EXIST(6033, "The department where employee %s belongs does not exist and cannot “%s”"),
	DEPT_MAIN_EMPLOYEE_DOES_NOT_EXIST(6034, "The department where employee %s belongs has no person in charge and cannot “%s”"),
	PARENT_MAIN_EMPLOYEE_DEPT_DOES_NOT_EXIST(6035, "%s employee's superior department has no person in charge and cannot “%s”"),
	DESIGNATION_EMPLOYEE_DEPT_DOES_NOT_EXIST(6036, "Can't find employee %s, can't find “%s”"),
	RESULT_CONFIRM_EMPLOYEE_NO_EXIST(6037, "Employee %s does not exist, can't open result confirmation!"),
	DEPT_CODE_ALREADY_EXISTS(6038, "Department code already exists"),
	PHONE_NUMBER_ERROR(6039, "%s:The format of the phone number is incorrect"),
	DATA_IS_DUPLICATED_WITH_MULTIPLE_UNIQUE_FIELDS(6040, "Data duplicates with multiple unique fields"),
	REQUIRED_FIELDS_ARE_NOT_FILLED(6041, "Required fields are not filled in"),
	ADDITIONAL_DEDUCTION_DATA_ERROR(6042, "Additional deduction item data error"),
	CUMULATIVE_TAX_OF_LAST_MONTH_DATA_ERROR(6043, "Accumulated tax data error as of last month"),
	DEFAULT_TEMPLATE_CANNOT_BE_DELETED(6044, "The default template cannot be deleted!"),
	EXIST_CHANGE_RECORD(6045, "The employee currently has a salary adjustment task in progress and cannot add another salary adjustment task!"),
	CHANGE_SALARY_NOT_FIX_SALARY(6046, "Employees who have adjusted their salary cannot be paid"),
	TOP_LEVEL_DEPARTMENT_CANNOT_BE_DELETED(6047, "The top department cannot be deleted"),
	YOU_HAVE_NO_EMPLOYEES_IN_HUMAN_RESOURCES(6048, "You have no employees in human resources"),
	NO_INTERVIEW_ARRANGEMENT(6049, "No inreview arrangement yet"),
	THE_FIELD_NUM_RESTRICT_ERROR(6050, "The numeric format of the custom form limit is incorrect"),
	THE_FIELD_DETAIL_TABLE_FORMAT_ERROR(6051, "Please set the specific fields in the form!"),
	HRM_FIELD_NUM_ERROR(6052, "There is at most each module" + Const.QUERY_MAX_SIZE + "fields"),
	;


	private int code;
	private String msg;

	HrmCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return msg;
	}
}
