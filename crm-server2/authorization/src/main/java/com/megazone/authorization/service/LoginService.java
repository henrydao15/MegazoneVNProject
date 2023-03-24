package com.megazone.authorization.service;

import com.megazone.authorization.entity.AuthorizationUser;
import com.megazone.core.common.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {
	/**
	 * Handling of login methods
	 *
	 * @param user    User object
	 * @param request
	 * @return Result
	 */
	public Result login(AuthorizationUser user, HttpServletResponse response, HttpServletRequest request);

	/**
	 * pre-login
	 *
	 * @param user User object
	 * @return Result
	 */
	public Result doLogin(AuthorizationUser user, HttpServletResponse response, HttpServletRequest request);

	/**
	 * ASD
	 *
	 * @param authentication
	 * @param url
	 * @param method
	 * @return Result
	 */
	Result permission(String authentication, String url, String method);

	/**
	 * sign out
	 *
	 * @param authentication
	 * @return Result
	 */
	Result logout(String authentication);
}
