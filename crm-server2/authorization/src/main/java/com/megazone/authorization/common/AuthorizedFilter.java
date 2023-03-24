package com.megazone.authorization.common;

import com.megazone.core.common.Const;
import com.megazone.core.common.Result;
import org.springframework.core.Ordered;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthorizedFilter implements AuthenticationEntryPoint, Ordered {


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		Result result = Result.error(AuthorizationCodeEnum.AUTHORIZATION_LOGIN);
		response.setContentType(Const.DEFAULT_CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.write(result.toJSONString());
		out.flush();
		out.close();
	}

	@Override
	public int getOrder() {
		return 9999;
	}
}
