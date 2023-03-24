package com.megazone.gateway.router;


import com.megazone.core.common.JSON;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.entity.UserExtraInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.exception.FeignServiceException;
import com.megazone.core.exception.NoLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;


@Component
@Order(-2)
@Slf4j
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {

	public WebExceptionHandler(ErrorAttributes errorAttributes, ApplicationContext applicationContext,
							   ServerCodecConfigurer serverCodecConfigurer) {
		super(errorAttributes, new ResourceProperties(), applicationContext);
		super.setMessageWriters(serverCodecConfigurer.getWriters());
		super.setMessageReaders(serverCodecConfigurer.getReaders());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
		Throwable error = getError(request);
		Map<String, Object> errorAttributes = this.getErrorAttributes(request, false);
		BodyInserter<Result, ReactiveHttpOutputMessage> result;
		log.error("Gateway request error：{}", JSON.toJSONString(errorAttributes));
		if (error instanceof CrmException) {
			result = BodyInserters.fromValue(Result.error((CrmException) error));
			return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(result);
		} else if (error instanceof FeignServiceException) {
			FeignServiceException fe = (FeignServiceException) error;
			result = BodyInserters.fromValue(Result.error(fe.status(), fe.getMessage()));
			return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(result);
		} else if (error instanceof NoLoginException) {
			Result<UserExtraInfo> resultData = Result.error(SystemCodeEnum.SYSTEM_NOT_LOGIN);
			resultData.setData(((NoLoginException) error).getInfo());
			result = BodyInserters.fromValue(resultData);
			return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(result);
		} else {
			Integer status = (Integer) errorAttributes.get("status");
			result = BodyInserters.fromValue(Result.error(SystemCodeEnum.parse(status)));
			return ServerResponse.status(HttpStatus.valueOf(status)).contentType(MediaType.APPLICATION_JSON).body(result);
		}
	}

//    @Bean
//    public BlockRequestHandler blockRequestHandler() {
//        BodyInserter<Result, ReactiveHttpOutputMessage> result = BodyInserters.fromValue(Result.error(SystemCodeEnum.SYSTEM_BAD_REQUEST));
//        return (exchange, t) -> ServerResponse.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(result);
//    }

}
