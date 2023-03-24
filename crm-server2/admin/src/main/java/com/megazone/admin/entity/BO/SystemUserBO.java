package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SystemUserBO implements Serializable {

	@ApiModelProperty
	@NotNull
	private String username;

	@ApiModelProperty
	@NotNull
	private String code;

	@ApiModelProperty
	@NotNull
	private String password;
}
