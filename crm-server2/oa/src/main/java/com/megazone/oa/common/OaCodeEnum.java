package com.megazone.oa.common;

import com.megazone.core.common.ResultCode;

public enum OaCodeEnum implements ResultCode {
	/**
	 * list
	 */
	OA_CODE_ENUM(500, ""), EVENT_ALREADY_DELETE(501, "The schedule has been deleted"), EXAMINE_END_TIME_IS_EARLIER_THAN_START_TIME(502, "Assessment end time is earlier than start time"), TRAVEL_END_TIME_IS_EARLIER_THAN_START_TIME(503, "Travel end time is earlier than start time"), THE_APPROVAL_FLOW_HAS_BEEN_DISABLED_OR_DELETED(504, "The approval flow has been disabled or deleted"), DURATION_MUST_BE_THREE_DIGITS(505, "The duration must be three digits"), CURRENT_USER_DOES_NOT_HAVE_APPROVAL_AUTHORITY(506, "The current user does not have approval authority"), THE_NAME_OF_THE_CUSTOM_FORM_CANNOT_BE_REPEATED(507, "Custom form name cannot be repeated"), SYSTEM_EXAMINE_CAN_NOT_MODIFY(508, "Editing is not currently allowed by the system"), LOG_ALREADY_DELETE(509, "Log has been deleted"), ANNOUNCEMENT_ALREADY_DELETE(510, "Announcement has been deleted"), EXAMINE_ALREADY_DELETE(511, "Assessment has been deleted"), TOTAL_REIMBURSEMENT_ERROR(512, "Please complete the details!"), TOTAL_AMOUNT_OF_EXPENSE_DETAILS_ERROR(513, "The total reimbursement amount (s%) should be greater than zero");

	private int code;
	private String msg;

	OaCodeEnum(int code, String msg) {
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
