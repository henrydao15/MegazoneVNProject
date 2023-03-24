package com.megazone.gateway.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.Const;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;
import com.megazone.gateway.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

/**
 * @author gateway
 */
@Component
public class GatewayFilter implements GlobalFilter, Ordered {

	protected static ThreadLocal<String> ip = new ThreadLocal<>();

	@Autowired
	private PermissionService permissionService;


	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String method = request.getMethodValue();
		String url = request.getPath().value();
		MultiValueMap<String, HttpCookie> cookies = request.getCookies();
		request.getCookies().keySet().forEach(str -> {
			if (Objects.equals("ServerIp", str)) {
				HttpCookie first = cookies.getFirst(str);
				ip.set(first != null ? first.getValue() : "");
			}
		});

		boolean isAuth = permissionService.ignoreAuthentication(url);
		if (isAuth) {
			return chain.filter(exchange);
		}

		Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		if (route != null) {
			Map<String, Object> metadata = route.getMetadata();
			if (Objects.equals(GatewayConst.ROUTER_INTERCEPT_OK, metadata.get(GatewayConst.ROUTER_INTERCEPT))) {
				return chain.filter(exchange);
			}
		}

		String token = request.getHeaders().getFirst(Const.TOKEN_NAME);
		/*

		 */
		if (url.startsWith("/adminFile/down/")) {
			token = request.getQueryParams().getFirst("c");
			if (StrUtil.isNotEmpty(token)) {
				request = exchange.getRequest().mutate().header(Const.TOKEN_NAME, token).build();
			}
		}

		String authentication = permissionService.invalidAccessToken(token, url, request.getCookies());
		if (StrUtil.isEmpty(authentication)) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NOT_LOGIN);
		}

		boolean permission = permissionService.hasPermission(authentication, url, method);
		if (!permission) {
			return unauthorized(exchange);
		}
		return chain.filter(exchange);
	}


	/**
	 * @param
	 */
	private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
		serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		Result result = Result.error(SystemCodeEnum.SYSTEM_NO_AUTH);
		DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(result.toJSONString().getBytes(CharsetUtil.systemCharset()));
		return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
	}

	@Override
	public int getOrder() {
		return -1;
	}
}
