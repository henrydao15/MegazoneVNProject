package com.megazone.core.exception;

import com.megazone.core.common.ResultCode;

public class CrmException extends RuntimeException implements ResultCode {

	private String msg;
	private int code;


	public CrmException(int code, String msg) {
		super(code + ":" + msg, null, true, true);
		this.code = code;
		this.msg = msg;
	}

	public CrmException(ResultCode resultCode) {
		this(resultCode.getCode(), resultCode.getMsg());
	}

	public CrmException(ResultCode resultCode, Object... args) {
		this(resultCode.getCode(), String.format(resultCode.getMsg(), args));
	}

	public CrmException(ResultCode resultCode, String str, Boolean flag) {
		this(resultCode.getCode(), resultCode.getMsg() + ":" + str);
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
