package com.megazone.authorization.common;

import com.megazone.core.common.ResultCode;

public enum AuthorizationCodeEnum implements ResultCode {
	//The system responds successfully
	AUTHORIZATION_LOGIN(1001, "Please log in first"),
	AUTHORIZATION_USERNAME_REQUIRED(1002, "Please enter username"),
	AUTHORIZATION_PASSWORD_REQUIRED(1003, "Please enter your password or SMS verification code"),
	AUTHORIZATION_LOGIN_NO_USER(1004, "Incorrect account or password"),
	AUTHORIZATION_LOGIN_ERR(1005, "Login authentication failed"),
	AUTHORIZATION_COMPANY_NOT_EXIST(1006, "The company was not found!"),
	AUTHORIZATION_USER_DISABLE_ERROR(1007, "Account is disabled!"),
	AUTHORIZATION_USER_SMS_CODE_ERROR(1008, "Verification code error!"),
	AUTHORIZATION_USER_DOES_NOT_EXIST(1009, "User does not exist, please register first!"),
	AUTHORIZATION_LOGIN_PASSWORD_TO_MANY_ERROR(1010, "Too many password errors, please try again in s%!"),
	CODE_CANNOT_BE_EMPTY(1011, "code cannot be empty!");


	private int code;
	private String msg;

	AuthorizationCodeEnum(int code, String msg) {
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
