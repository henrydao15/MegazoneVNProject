package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AdminInitDataBO {

	@ApiModelProperty
	private String password;

	@ApiModelProperty
	private String temporaryCode;

	@ApiModelProperty
	private List<String> modules;

}
