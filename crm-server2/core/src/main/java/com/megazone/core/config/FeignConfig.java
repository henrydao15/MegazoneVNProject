package com.megazone.core.config;

import com.megazone.core.common.Const;
import com.megazone.core.common.Result;
import com.megazone.core.exception.CrmException;
import com.megazone.core.exception.FeignServiceException;
import feign.FeignException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class FeignConfig implements RequestInterceptor {

	@Bean
	public Decoder feignDecoder() {
		return new ResponseEntityDecoder(new FeignDecode(feignHttpMessageConverter()));
	}

	private ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
		final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new GateWayMappingJackson2HttpMessageConverter());

		return () -> httpMessageConverters;
	}

	@Override
	public void apply(RequestTemplate requestTemplate) {
		ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			requestTemplate.header(Const.TOKEN_NAME, request.getHeader(Const.TOKEN_NAME));
			requestTemplate.header("proxyHost", request.getHeader("proxyHost"));
		}
	}

	private class GateWayMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
		GateWayMappingJackson2HttpMessageConverter() {
			List<MediaType> mediaTypes = new ArrayList<>();
			mediaTypes.add(MediaType.APPLICATION_JSON);
			setSupportedMediaTypes(mediaTypes);
		}
	}

	public class FeignDecode extends SpringDecoder {

		FeignDecode(ObjectFactory<HttpMessageConverters> messageConverters) {
			super(messageConverters);
		}

		@Override
		public Object decode(Response response, Type type) throws FeignException, IOException, CrmException {
			Object data = super.decode(response, type);
			if (data instanceof Result) {
				if (!((Result) data).hasSuccess()) {
					throw new FeignServiceException(((Result) data).getCode(), ((Result) data).getMsg());
				}
			}
			return data;
		}
	}
}
