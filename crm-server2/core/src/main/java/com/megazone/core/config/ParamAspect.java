package com.megazone.core.config;

import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.Const;
import com.megazone.core.entity.UserExtraInfo;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.NoLoginException;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.redis.Redis;
import com.megazone.core.servlet.LoginFromCookie;
import com.megazone.core.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class ParamAspect implements Ordered {

	@Autowired
	private Redis redis;

	@Autowired
	private AdminService adminService;

	@Around("(execution(* com.megazone.*.controller..*(..))||execution(* com.megazone.*.*.controller..*(..))) && execution(@(org.springframework.web.bind.annotation.*Mapping) * *(..))  && !execution(@(com.megazone.core.common.ParamAspect) * *(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
		HttpServletRequest request = attributes.getRequest();
		try {
			String token = request.getHeader(Const.TOKEN_NAME);
			if (StrUtil.isEmpty(token)) {
				if (point instanceof MethodInvocationProceedingJoinPoint) {
					Method targetMethod = ((MethodSignature) point.getSignature()).getMethod();
					LoginFromCookie fromCookie = targetMethod.getAnnotation(LoginFromCookie.class);
					if (fromCookie != null) {
						for (Cookie cookie : request.getCookies()) {
							if (Const.TOKEN_NAME.equals(cookie.getName())) {
								token = cookie.getValue();
								break;
							}
						}
					}
				}
			}
			UserInfo info = null;
			if (StrUtil.isNotEmpty(token)) {
				Object data = redis.get(token);
				if (data instanceof UserExtraInfo) {
					throw new NoLoginException((UserExtraInfo) data);
				} else if (data instanceof UserInfo) {
					info = (UserInfo) data;
				}
				if (info != null) {
					info.setRequest(request);
					info.setResponse(attributes.getResponse());
					UserUtil.setUser(info);
					UserUtil.userExpire(token);
				}
			}
			if (info == null) {
				//TODO has an empty user object when not logged in
				info = new UserInfo();
				info.setRequest(request);
				info.setResponse(attributes.getResponse());
				UserUtil.setUser(info);
			}
			return point.proceed();
		} finally {
			UserUtil.removeUser();
		}
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
