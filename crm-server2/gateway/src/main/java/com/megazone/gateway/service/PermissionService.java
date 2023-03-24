package com.megazone.gateway.service;

import org.springframework.http.HttpCookie;
import org.springframework.util.MultiValueMap;

public interface PermissionService {

	boolean hasPermission(String authentication, String url, String method);

	String invalidAccessToken(String authentication, String url, MultiValueMap<String, HttpCookie> cookies);

	boolean ignoreAuthentication(String url);
}
