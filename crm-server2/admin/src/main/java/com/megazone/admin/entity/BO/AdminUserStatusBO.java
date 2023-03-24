package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@ApiModel
public class AdminUserStatusBO {

	@ApiModelProperty
	private List<Long> ids;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private String password;

}
