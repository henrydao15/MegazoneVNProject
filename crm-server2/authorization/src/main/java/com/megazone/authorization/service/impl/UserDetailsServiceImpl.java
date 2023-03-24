//package com.megazone.authorization.service.impl;
//
//import cn.hutool.core.util.IdUtil;
//import com.megazone.authorization.common.AuthException;
//import com.megazone.authorization.common.AuthorizationCodeEnum;
//import com.megazone.authorization.common.LoginLogUtil;
//import com.megazone.authorization.entity.AdminUserStatusBO;
//import com.megazone.authorization.entity.AuthorizationUser;
//import com.megazone.authorization.entity.AuthorizationUserInfo;
//import com.megazone.authorization.entity.VO.LoginVO;
//import com.megazone.authorization.service.AdminUserService;
//import com.megazone.authorization.service.LoginService;
//import com.megazone.core.common.LoginType;
//import com.megazone.core.common.Result;
//import com.megazone.core.common.SystemCodeEnum;
//import com.megazone.core.common.cache.AdminCacheKey;
//import com.megazone.core.common.cache.CrmCacheKey;
//import com.megazone.core.entity.UserInfo;
//import com.megazone.core.exception.CrmException;
//import com.megazone.core.feign.admin.entity.LoginLogEntity;
//import com.megazone.core.feign.admin.service.LogService;
//import com.megazone.core.redis.Redis;
//import com.megazone.core.servlet.ApplicationContextHolder;
//import com.megazone.core.utils.UserUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService, LoginService {
//
//	@Autowired
//	private AdminUserService adminUserService;
//
//	@Autowired
//	private AuthenticationManager authenticationManager;
//
//	@Autowired
//	private Redis redis;
//
//
//	@Autowired
//	private LoginLogUtil loginLogUtil;
//
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Result result = adminUserService.findByUsername(username);
//		if (result.hasSuccess()) {
//			return new AuthorizationUser().setUserInfoList((List<Object>) result.getData());
//		}
//		throw new UsernameNotFoundException(null);
//	}
//
//	@Override
//	public Result login(AuthorizationUser user, HttpServletResponse response, HttpServletRequest request) {
//		LoginLogEntity logEntity = loginLogUtil.getLogEntity(request);
//		String token = IdUtil.simpleUUID();
//		UserInfo userInfo = user.toUserInfo();
//		logEntity.setUserId(userInfo.getUserId());
//		logEntity.setRealname(userInfo.getRealname());
//		if (userInfo.getStatus() == 0) {
//			logEntity.setAuthResult(2);
//			logEntity.setFailResult(AuthorizationCodeEnum.AUTHORIZATION_USER_DISABLE_ERROR.getMsg());
//			ApplicationContextHolder.getBean(LogService.class).saveLoginLog(logEntity);
//			throw new CrmException(AuthorizationCodeEnum.AUTHORIZATION_USER_DISABLE_ERROR);
//		}
//		userInfo.setRoles(adminUserService.queryUserRoleIds(userInfo.getUserId()).getData());
//		UserUtil.userToken(token, userInfo, user.getType());
//		if (userInfo.getStatus() == 2) {
//			adminUserService.activateUser(AdminUserStatusBO.builder().status(1).ids(Collections.singletonList(userInfo.getUserId())).build());
//		}
//		ApplicationContextHolder.getBean(LogService.class).saveLoginLog(logEntity);
//		return Result.ok(new LoginVO().setAdminToken(token));
//	}
//
//	@Override
//	public Result doLogin(AuthorizationUser user, HttpServletResponse response, HttpServletRequest request) {
//		LoginType loginType = LoginType.valueOf(user.getLoginType());
//		if (loginType.equals(LoginType.PASSWORD) || loginType.equals(LoginType.SMS_CODE)) {
//			String key = AdminCacheKey.PASSWORD_ERROR_CACHE_KEY + user.getUsername().trim();
//			Integer errorNum = redis.get(key);
//			if (errorNum != null && errorNum > 2) {
//				Integer second = Optional.ofNullable(redis.ttl(key)).orElse(0L).intValue();
//				if (second > 0) {
//					String errorTimeDesc = this.getErrorTimeDesc(second);
//					return Result.error(AuthorizationCodeEnum.AUTHORIZATION_LOGIN_PASSWORD_TO_MANY_ERROR, "Too many password errors, please try again after " + errorTimeDesc + "!");
//				}
//			}
//		}
//		try {
//			AbstractAuthenticationToken authenticationToken;
//			switch (loginType) {
//				case PASSWORD:
//					authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername().trim(), user.getPassword().trim());
//					break;
//				default:
//					authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername().trim(), user.getPassword().trim());
//					break;
//			}
//			Authentication authentication = authenticationManager.authenticate(authenticationToken);
//			AuthorizationUserInfo userInfo = (AuthorizationUserInfo) authentication.getDetails();
//			if (userInfo.getAuthorizationUserList().size() == 0) {
//				return this.handleLoginPassWordToManyError(user.getUsername().trim());
//			}
//			return login(userInfo.getAuthorizationUserList().get(0).setType(user.getType()), response, request);
//		} catch (AuthException e) {
//			return Result.error(e.getResultCode());
//		} catch (BadCredentialsException e) {
//			return this.handleLoginPassWordToManyError(user.getUsername().trim());
//		}
//
//	}
//
//	private String getErrorTimeDesc(Integer second) {
//		String errorTimeDesc;
//		if (Arrays.asList(300, 240, 180, 120, 60).contains(second)) {
//			errorTimeDesc = second / 60 + "minutes";
//		} else if (second < 60) {
//			errorTimeDesc = second + "seconds";
//		} else {
//			errorTimeDesc = second / 60 + "minutes" + second % 60 + "seconds";
//		}
//		return errorTimeDesc;
//	}
//
//	private Result handleLoginPassWordToManyError(String userName) {
//		String key = AdminCacheKey.PASSWORD_ERROR_CACHE_KEY + userName;
//		Integer errorNum = redis.get(key);
//		if (errorNum == null) {
//			redis.setex(AdminCacheKey.PASSWORD_ERROR_CACHE_KEY + userName, 60 * 3, 1);
//		} else if (errorNum < 3) {
//			Integer defineTime = errorNum == 2 ? 60 * 2 : 60 * 3;
//			redis.setex(AdminCacheKey.PASSWORD_ERROR_CACHE_KEY + userName, defineTime, errorNum + 1);
//		}
//		return Result.error(AuthorizationCodeEnum.AUTHORIZATION_LOGIN_NO_USER);
//	}
//
//	@Override
//	public Result permission(String authentication, String url, String method) {
//		UserInfo userInfo = redis.get(authentication);
//		if (userInfo == null) {
//			throw new CrmException(SystemCodeEnum.SYSTEM_NOT_LOGIN);
//		}
//		Long userId = userInfo.getUserId();
//		String key = userId.toString();
//		List<String> noAuthMenuUrls = redis.get(key);
//		if (noAuthMenuUrls == null) {
//			noAuthMenuUrls = adminUserService.queryNoAuthMenu(userId).getData();
//		}
//		boolean permission = isHasPermission(noAuthMenuUrls, url);
//		return permission ? Result.ok() : Result.noAuth();
//	}
//
//	@Override
//	public Result logout(String authentication) {
//		Object data = redis.get(authentication);
//		if (data instanceof UserInfo) {
//			UserInfo userInfo = (UserInfo) data;
//			redis.del(authentication);
//			redis.del(AdminCacheKey.USER_AUTH_CACHE_KET + userInfo.getUserId());
//			redis.del(CrmCacheKey.CRM_AUTH_USER_CACHE_KEY + userInfo.getUserId());
//		}
//		return Result.ok();
//	}
//
//	private boolean isHasPermission(List<String> noAuthMenuUrls, String url) {
//		//User information is missing | error
//		if (noAuthMenuUrls == null) {
//			return false;
//		}
//		//administrator
//		if (noAuthMenuUrls.size() == 0) {
//			return true;
//		}
//		// no permissions
//		if (noAuthMenuUrls.size() == 1 && "/*/**".equals(noAuthMenuUrls.get(0))) {
//			return false;
//		}
//		boolean permission = true;
//		for (String noAuthMenuUrl : noAuthMenuUrls) {
//			if (noAuthMenuUrl.contains("*")) {
//				if (noAuthMenuUrl.contains(",")) {
//					boolean isNoAuth = false;
//					for (String noAuthUrl : noAuthMenuUrl.split(",")) {
//						if (url.startsWith(noAuthUrl.replace("*", ""))) {
//							isNoAuth = true;
//							break;
//						}
//					}
//					if (isNoAuth) {
//						permission = false;
//						break;
//					}
//				} else {
//					if (url.startsWith(noAuthMenuUrl.replace("*", ""))) {
//						permission = false;
//						break;
//					}
//				}
//			} else {
//				if (noAuthMenuUrl.contains(",")) {
//					if (Arrays.asList(noAuthMenuUrl.split(",")).contains(url)) {
//						permission = false;
//						break;
//					}
//				} else {
//					if (noAuthMenuUrl.equals(url)) {
//						permission = false;
//						break;
//					}
//				}
//			}
//		}
//		return permission;
//	}
//}
