package com.megazone.core.exception;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.ResultCode;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.entity.UserExtraInfo;
import com.netflix.client.ClientException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;


/**
 *
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {


	@ExceptionHandler(value = Exception.class)
	public Result defaultException(Exception ex) {
		//TODO
		log.error("", ex);
		if (ex instanceof ResultCode) {
			return R.error(((ResultCode) ex).getCode(), ((ResultCode) ex).getMsg());
		}
		return Result.error(SystemCodeEnum.SYSTEM_ERROR);
	}

	@ExceptionHandler(value = AuthException.class)
	public Result authException() {
		return Result.noAuth();
	}

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public Result methodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("", e);
		return Result.noAuth();
	}

	@ExceptionHandler(value = ServletException.class)
	public Result servletException(ServletException e) {
        /*
          TODO NoHandlerFoundExceptiongateway，ServletException
         */
		if (e instanceof NoHandlerFoundException) {
			log.error(":", ((NoHandlerFoundException) e).getRequestURL());
			return Result.error(SystemCodeEnum.SYSTEM_NO_FOUND);
		}
		log.error("ServletException:", e);
		return Result.error(SystemCodeEnum.SYSTEM_ERROR);
	}

	@ExceptionHandler(value = NoLoginException.class)
	public Result<UserExtraInfo> noLoginException(NoLoginException e) {
		Result<UserExtraInfo> result = Result.error(SystemCodeEnum.SYSTEM_NOT_LOGIN);
		result.setData(e.getInfo());
		return result;
	}

	@ExceptionHandler(value = BindException.class)
	public Result validException(BindException e) {
		log.error("BindException:", e);
		if (e.getGlobalError() != null) {
			return Result.error(SystemCodeEnum.SYSTEM_NO_VALID, e.getGlobalError().getDefaultMessage());
		} else if (e.getFieldError() != null) {
			return Result.error(SystemCodeEnum.SYSTEM_NO_VALID, e.getFieldError().getDefaultMessage());
		} else {
			return Result.error(SystemCodeEnum.SYSTEM_NO_VALID);
		}
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public Result argumentNotValidException(MethodArgumentNotValidException e) {
		log.error("MethodArgumentNotValidException:", e);
		BindingResult bindingResult = e.getBindingResult();
		if (bindingResult.getGlobalError() != null) {
			return Result.error(SystemCodeEnum.SYSTEM_NO_VALID, bindingResult.getGlobalError().getDefaultMessage());
		} else if (bindingResult.getFieldError() != null) {
			return Result.error(SystemCodeEnum.SYSTEM_NO_VALID, bindingResult.getFieldError().getDefaultMessage());
		} else {
			return Result.error(SystemCodeEnum.SYSTEM_NO_VALID);
		}
	}

	@ExceptionHandler(value = ClientException.class)
	public Result clientException() {
		return Result.error(SystemCodeEnum.SYSTEM_SERVER_ERROR);
	}

	@ExceptionHandler(value = FeignException.class)
	public Result feignException(FeignException ex) {
		log.error("feign：", ex);
		String message = ex.getMessage();
		if (StrUtil.isNotEmpty(message) && ReUtil.contains("[\\u4e00-\\u9fa5]", message)) {
			return Result.error(ex.status(), message);
		} else {
			return Result.error(SystemCodeEnum.SYSTEM_SERVER_ERROR);
		}
	}
}
