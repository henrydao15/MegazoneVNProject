package com.megazone.gateway.service;

import com.megazone.core.common.Const;
import com.megazone.core.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "authorization", fallback = AuthService.AuthServiceImpl.class)
public interface AuthService {
	@RequestMapping(value = "/permission")
	Result hasPermission(@RequestHeader(Const.TOKEN_NAME) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);

	@Component
	class AuthServiceImpl implements AuthService {

		@Override
		public Result hasPermission(String authentication, String url, String method) {
			return Result.noAuth();
		}
	}
}
