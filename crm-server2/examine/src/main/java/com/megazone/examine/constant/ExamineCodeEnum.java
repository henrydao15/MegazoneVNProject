package com.megazone.examine.constant;

import com.megazone.core.common.ResultCode;

/**
 * Approval error enumeration
 * 4200-4300
 */
public enum ExamineCodeEnum implements ResultCode {

	/**
	 * list
	 */
	EXAMINE_START_ERROR(4200, "Can only have one enabled approval process"),
	EXAMINE_RECHECK_PASS_ERROR(4201, "The review has passed and cannot be withdrawn"),
	EXAMINE_AUTH_ERROR(4202, "The review has passed and cannot be withdrawn"),
	EXAMINE_ROLE_NO_USER_ERROR(4203, "No personnel detected in the selected role, please verify!"),
	EXAMINE_NAME_NO_USER_ERROR(4204, "Duplicate approval flow name, please verify!"),
	EXAMINE_SPECIAL_TYPE_NOT_DELETE_ERROR(4204, "System default approval does not allow deletion!"),
	;

	private int code;
	private String msg;

	ExamineCodeEnum(int code, String msg) {
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
