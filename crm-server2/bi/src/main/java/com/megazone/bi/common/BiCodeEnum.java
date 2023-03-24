package com.megazone.bi.common;

import com.megazone.core.common.ResultCode;


public enum BiCodeEnum implements ResultCode {
	//Customer module management
	BI_DATE_PARSE_ERROR(3101, "Date parsing error!"),
	BI_ACHIEVEMEN_DATA_PARSE_ERROR(3102, "Incorrect amount setting, please verify!");

	private int code;
	private String msg;

	BiCodeEnum(int code, String msg) {
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
