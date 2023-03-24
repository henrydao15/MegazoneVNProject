package com.megazone.core.exception;

import com.megazone.core.entity.UserExtraInfo;

public class NoLoginException extends RuntimeException {

	private UserExtraInfo info;

	public NoLoginException() {
		this(null);
	}

	public NoLoginException(UserExtraInfo info) {
		super("please log in first!", null, false, false);
		this.info = info;
	}

	public UserExtraInfo getInfo() {
		return info;
	}
}
