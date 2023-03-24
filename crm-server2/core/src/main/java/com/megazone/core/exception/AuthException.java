package com.megazone.core.exception;

public class AuthException extends RuntimeException {

	public AuthException() {
		super();
	}

	public AuthException(String message, Throwable cause) {
		super(message, cause);
	}
}
