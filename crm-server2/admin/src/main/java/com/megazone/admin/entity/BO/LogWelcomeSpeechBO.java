package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@Data
@ApiModel
public class LogWelcomeSpeechBO {

	@ApiModelProperty(value = "Set ID", required = true)
	@NotNull
	private Integer settingId;

	@ApiModelProperty(value = "Log Welcome", required = true)
	@NotNull
	@Size(max = 100)
	private String value;

}
