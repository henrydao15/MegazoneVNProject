package com.megazone.admin.common;

import com.megazone.core.common.ResultCode;

public enum AdminCodeEnum implements ResultCode {
	//Customer module management
	ADMIN_MODULE_CLOSE_ERROR(1101, "Customer management module cannot be closed"),
	ADMIN_DATA_EXIST_ERROR(1102, "Data does not exist"),
	ADMIN_PASSWORD_INTENSITY_ERROR(1106, "The password must consist of 6-20 letters and numbers"),
	ADMIN_USER_EXIST_ERROR(1107, "User already exists!"),
	ADMIN_PARENT_USER_ERROR(1109, "This user's subordinate cannot be set as a direct superior!"),
	ADMIN_PARENT_DEPT_ERROR(1110, "This department's subordinate cannot be set as a direct superior department!"),
	ADMIN_DEPT_REMOVE_EXIST_USER_ERROR(1111, "This department has employees and can not be deleted!"),
	ADMIN_DEPT_REMOVE_EXIST_DEPT_ERROR(1112, "This department has subordinate departments and can not be deleted!"),
	ADMIN_USER_NOT_ROLE_ERROR(1113, "Please set a role for the user first"),
	ADMIN_USER_NOT_DEPT_ERROR(1114, "Please set the department for the user first"),
	ADMIN_SUPER_USER_DISABLED_ERROR(1116, "Super administrator user cannot be disabled"),
	ADMIN_ROLE_NAME_EXIST_ERROR(1117, "Role name already exists"),
	ADMIN_PHONE_CODE_ERROR(1118, "Phone verification code error!"),
	ADMIN_PHONE_REGISTER_ERROR(1119, "The phone number has already been registered!"),
	ADMIN_PHONE_VERIFY_ERROR(1120, "Phone number verification error!"),
	ADMIN_PHONE_EXIST_ERROR(1121, "The phone number does not exist!"),
	ADMIN_SMS_SEND_FREQUENCY_ERROR(1122, "The frequency of SMS messages sent is too high, please try again later!"),
	ADMIN_SMS_SEND_ERROR(1123, "Failed to send verification code, please try again later!"),
	ADMIN_MANAGE_UPDATE_ERROR(1124, "The super administrator account needs to go to Wukong Personal Center to modify the mobile phone number information!"),
	ADMIN_USER_NOT_EXIST_ERROR(1125, "User does not exist!"),
	ADMIN_ACCOUNT_ERROR(1126, "The account cannot be the same as the original account!"),
	ADMIN_PASSWORD_ERROR(1127, "Password input error!"),
	ADMIN_USERNAME_EDIT_ERROR(1128, "User name cannot be modified!"),
	ADMIN_USER_HIS_TABLE_ERROR(1129, "The number of users has reached the upper limit!"),
	ADMIN_PARENT_USER_ERROR1(1130, "The direct superior cannot be himself!"),
	ADMIN_PRODUCT_DATA_ERROR(1131, "Product does not exist!"),
	ADMIN_MARKETING_DATA_ERROR(1132, "Insufficient points!"),
	ADMIN_USER_NEEDS_AT_LEAST_ONE_ROLE(1133, "User needs at least one role!"),
	ADMIN_PASSWORD_EXPIRE_ERROR(1134, "Password verification has expired, please verify again!"),
	ADMIN_PASSWORD_INVALID_ERROR(1135, "Invalid password!"),
	ADMIN_ROLE_NOT_EXIST_ERROR(1136, "Please associate a role first!"),
	ADMIN_LANGUAGE_PACK_NAME_ERROR(1137, "The language pack name is wrong!"),
	ADMIN_LANGUAGE_PACK_EXIST_USER_ERROR(1138, "This language pack is being used by a user and cannot be deleted!"),
	ADMIN_LANGUAGE_PACK_CHOINESE_ERROR(1139, "Missing Chinese language pack!"),
	ADMIN_DEFAULT_ROLE_CANNOT_BE_DELETED(1140, "The default role cannot be deleted!"),
	ADMIN_USER_REAL_NAME_EXIST_ERROR(1141, "Duplicate user name!"),
	ADMIN_DEPT_NOT_EXIST_ERROR(1142, "Department no longer exists!"),
	/**
	 * Enterprise WeChat error code starts from 1200
	 */
	ADMIN_CP_ERROR(1200, "%s!"),
	ADMIN_CP_DOES_NOT_EXIST(1201, "The enterprise does not exist, please bind the application first!"),
	;

	private int code;
	private String msg;

	AdminCodeEnum(int code, String msg) {
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
