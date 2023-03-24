package com.megazone.authorization.common;

import cn.hutool.core.util.StrUtil;
import com.megazone.authorization.entity.AuthorizationUser;
import com.megazone.core.common.Const;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.redis.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationTokenFilter extends OncePerRequestFilter implements Ordered {


	@Autowired
	private Redis redis;


	@Override
	protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws ServletException, IOException {
		String token = request.getHeader(Const.TOKEN_NAME);
		logger.info(request.getRequestURI());
		response.setContentType(Const.DEFAULT_CONTENT_TYPE);
		if (StrUtil.isNotEmpty(token)) {
			Object user = redis.get(token);
			if (user instanceof UserInfo) {
				AuthorizationUser authorizationUser = AuthorizationUser.toAuthorizationUser((UserInfo) user);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authorizationUser, null, new ArrayList<>());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public int getOrder() {
		return -1;
	}
}
