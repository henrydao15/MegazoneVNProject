package com.megazone.gateway.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.megazone.core.common.Const;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.entity.UserExtraInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.exception.NoLoginException;
import com.megazone.core.redis.Redis;
import com.megazone.gateway.config.GatewayConfiguration;
import com.megazone.gateway.config.SwaggerProvider;
import com.megazone.gateway.service.AuthService;
import com.megazone.gateway.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {
	/**
	 *
	 */
	private static final String[] AUTH_SPECIAL_URLS = {"/adminFile/down/*", "/crmPrint/down", "/crmPrint/preview.pdf"};
	/**
	 *
	 */
	private static final List<String> NOT_AUTH_URLS = Lists.newArrayList("/crmMarketing/queryMarketingId", "/crmMarketing/queryAddField", "/crmMarketing/saveMarketingInfo", "/adminUser/queryLoginUser", "/crmCall/upload", "/adminUser/querySystemStatus", "/adminUser/initUser");
	@Autowired
	private AuthService authService;
	@Autowired
	private GatewayConfiguration configuration;
	@Autowired
	private Redis redis;

	/**
	 * @param authentication
	 * @param url            url
	 * @param method
	 * @return true/false
	 */
	@Override
	public boolean hasPermission(String authentication, String url, String method) {
		Result result = authService.hasPermission(authentication, url, method);
		return result.hasSuccess();
	}

	/**
	 * @param authentication
	 * @param url
	 * @param cookies
	 * @return true
	 */
	@Override
	public String invalidAccessToken(String authentication, String url, MultiValueMap<String, HttpCookie> cookies) {
		if (StrUtil.isEmpty(authentication)) {
			boolean isSpecialUrl = false;
			for (String specialUrl : AUTH_SPECIAL_URLS) {
				if (specialUrl.contains("*")) {
					if (url.startsWith(specialUrl.replace("*", ""))) {
						isSpecialUrl = true;
						break;
					}
				} else {
					if (url.equals(specialUrl)) {
						isSpecialUrl = true;
						break;
					}
				}
			}
			if (isSpecialUrl) {
				HttpCookie first = cookies.getFirst(Const.TOKEN_NAME);
				authentication = first != null ? first.getValue() : "";
			}
		}
		if (StrUtil.isEmpty(authentication)) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NOT_LOGIN);
		}
		Object data = redis.get(authentication);
		if (data == null) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NOT_LOGIN);
		}
		if (data instanceof UserExtraInfo) {
			throw new NoLoginException((UserExtraInfo) data);
		}
		return authentication;
	}

	/**
	 * @param url url
	 * @return true
	 */
	@Override
	public boolean ignoreAuthentication(String url) {
		boolean isAuth = Optional.ofNullable(configuration.getIgnoreUrl()).orElse(ListUtil.empty()).stream().anyMatch(url::startsWith);
		if (!isAuth) {
			if (url.endsWith(SwaggerProvider.API_URI) || NOT_AUTH_URLS.contains(url)) {
				isAuth = true;
			}
		}
		return isAuth;
	}
}
