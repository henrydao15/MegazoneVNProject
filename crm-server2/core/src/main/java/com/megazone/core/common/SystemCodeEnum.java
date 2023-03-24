package com.megazone.core.common;

/**
 * System response error code enumeration class
 */

public enum SystemCodeEnum implements ResultCode {
	//The system responds successfully
	SYSTEM_OK(0, "success"),
	// uncaught error
	SYSTEM_ERROR(500, "Network error, please try again later"),

	SYSTEM_NOT_LOGIN(302, "Please log in first!"),
	//access denied
	SYSTEM_BAD_REQUEST(403, "The request frequency is too fast, please try again later"),
	// no access
	SYSTEM_NO_AUTH(401, "No permission to operate"),
	//Resource not found
	SYSTEM_NO_FOUND(404, "Resource not found"),
	//Resource not found
	SYSTEM_NO_VALID(400, "Parameter validation error"),
	// request method error
	SYSTEM_METHOD_ERROR(405, "Request method error"),
	//Request timed out
	SYSTEM_REQUEST_TIMEOUT(408, "Request timed out"),
	//Service call exception
	SYSTEM_SERVER_ERROR(1001, "Service call exception"),
	//Enterprise information has expired
	SYSTEM_NO_SUCH_PARAMENT_ERROR(1003, "Parameter does not exist!"),
	// upload file failed
	SYSTEM_UPLOAD_FILE_ERROR(1004, "File upload failed!"),
	;

	private int code;
	private String msg;

	SystemCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static SystemCodeEnum parse(Integer status) {
		for (SystemCodeEnum value : values()) {
			if (value.getCode() == status) {
				return value;
			}
		}
		return SystemCodeEnum.SYSTEM_ERROR;
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
