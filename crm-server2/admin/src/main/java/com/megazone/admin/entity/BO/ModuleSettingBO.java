package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ToString
@Data
@ApiModel
public class ModuleSettingBO {

	@ApiModelProperty(value = "Set ID", required = true)
	@NotNull
	private Integer settingId;

	@ApiModelProperty(value = "status 1: enabled 0: disabled", required = true, allowableValues = "0,1")
	@NotNull(message = "Status cannot be null")
	@Max(1)
	@Min(0)
	private Integer status;

	@ApiModelProperty(value = "name")
	private String name;

}
