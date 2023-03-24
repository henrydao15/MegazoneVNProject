package com.megazone.admin.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@ApiModel
public class ModuleSettingVO {

	@ApiModelProperty(value = "Set ID", required = true)
	private Integer settingId;

	@ApiModelProperty(value = "module", required = true)
	private String module;

	@ApiModelProperty(value = "status 1: enabled 0: disabled", required = true, allowableValues = "0,1")
	private Integer status;

	@ApiModelProperty(value = "Type 1: Normal application 2: Value-added application 3: Unpublished application", required = true, allowableValues = "1,2,3")
	private String type;

	@ApiModelProperty(value = "name", required = true)
	private String name;
}
