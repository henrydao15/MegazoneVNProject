package com.megazone.core.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.megazone.core.common.Const;
import com.megazone.core.common.SystemCodeEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * swagger configuration
 */

@Configuration
@EnableKnife4j
@EnableSwagger2WebMvc
public class SwaggerConfig {

	@Value("${spring.application.name:core}")
	private String desc;

	@Bean
	public Docket createRestApi() {
		List<ResponseMessage> responseMessageList = new ArrayList<>();
		// global response code
		responseMessageList.add(new ResponseMessageBuilder().code(200).message(SystemCodeEnum.SYSTEM_OK.getMsg()).responseModel(new ModelRef("Result")).build());
		responseMessageList.add(new ResponseMessageBuilder().code(400).message(SystemCodeEnum.SYSTEM_NO_VALID.getMsg()).responseModel(new ModelRef("Result")).build());
		responseMessageList.add(new ResponseMessageBuilder().code(401).message(SystemCodeEnum.SYSTEM_NO_AUTH.getMsg()).responseModel(new ModelRef("Result")).build());
		responseMessageList.add(new ResponseMessageBuilder().code(403).message(SystemCodeEnum.SYSTEM_BAD_REQUEST.getMsg()).responseModel(new ModelRef("Result")).build());
		responseMessageList.add(new ResponseMessageBuilder().code(500).message(SystemCodeEnum.SYSTEM_ERROR.getMsg()).responseModel(new ModelRef("Result")).build());
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				//todo only scans with method annotations
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build();
		// do not use the default response class
		docket.useDefaultResponseMessages(false);
		// ignored classes
		docket.ignoredParameterTypes(HttpServletRequest.class, HttpServletResponse.class);
		List<Parameter> parameterList = new ArrayList<>();
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		parameterList.add(parameterBuilder.name(Const.TOKEN_NAME).modelRef(new ModelRef("string")).parameterType("header").description("User login credentials").required(false).build());
		docket.globalOperationParameters(parameterList);
		docket.globalResponseMessage(RequestMethod.POST, responseMessageList);
		docket.globalResponseMessage(RequestMethod.GET, responseMessageList);
		docket.globalResponseMessage(RequestMethod.DELETE, responseMessageList);
		return docket;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(desc)
				.version(Const.PROJECT_VERSION)
				.build();
	}
}
