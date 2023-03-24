package com.megazone.authorization.controller;

import com.megazone.core.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "User login related interface")
@Slf4j
public class AuthController {

//	@Autowired
//	private LoginService loginService;
//
//
//	@RequestMapping(value = "/permission")
//	@ParamAspect
//	public Result permission(@RequestParam("url") String url, @RequestParam("method") String method, HttpServletRequest request) {
//		String token = request.getHeader(Const.TOKEN_NAME);
//		String proxyHost = request.getHeader("proxyHost");
//		return loginService.permission(token, url, proxyHost);
//	}
//
//	/**
//	 * Login method, current limit is handled by sentinel
//	 */
//	@PostMapping(value = "/login")
//	@ApiOperation(tags = "User Login", httpMethod = "POST", value = "/doLogin")
//	public Result doLogin(@Valid @RequestBody AuthorizationUser user, HttpServletResponse response, HttpServletRequest request) {
//		if (StrUtil.isNotEmpty(user.getUsername())) {
//			if (StrUtil.trimToNull(user.getUsername()) == null) {
//				return Result.error(AuthorizationCodeEnum.AUTHORIZATION_USERNAME_REQUIRED);
//			}
//			if (StrUtil.trimToNull(user.getPassword()) == null && StrUtil.trimToNull(user.getSmscode()) == null) {
//				return Result.error(AuthorizationCodeEnum.AUTHORIZATION_PASSWORD_REQUIRED);
//			}
//		}
//		return loginService.doLogin(user, response, request);
//	}
//
//	@RequestMapping(value = "/logout")
//	@ApiOperation(tags = "User Logout", httpMethod = "GET", value = "/logout")
//	@ParamAspect
//	public Result logout(HttpServletRequest request, HttpServletResponse response) {
//		String token = request.getHeader(Const.TOKEN_NAME);
//		if (StrUtil.isNotEmpty(token)) {
//			loginService.logout(token);
//		}
//		String serverName = StrUtil.isNotEmpty(request.getHeader("proxyHost")) ? request.getHeader("proxyHost") : request.getServerName();
//		int index = serverName.indexOf(".");
//		for (String user : Arrays.asList(Const.TOKEN_NAME, "User")) {
//			Cookie cookie = ServletUtil.getCookie(request, user);
//			if (cookie != null) {
//				cookie.setMaxAge(0);
//				cookie.setValue(null);
//				cookie.setPath("/");
//				cookie.setDomain(index != -1 ? serverName.substring(index) : serverName);
//				response.addCookie(cookie);
//			}
//		}
//
//		return Result.ok();
//	}

	@RequestMapping(value = "/verify")
	@ApiOperation(tags = "User verify", httpMethod = "GET", value = "/verify")
	public Result verify() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SimpleKeycloakAccount account = ((SimpleKeycloakAccount) authentication.getDetails());
		KeycloakSecurityContext context = account.getKeycloakSecurityContext();

		AccessToken token = context.getToken();
		System.out.println(token);

		return Result.ok(context.getIdToken());
	}
}
