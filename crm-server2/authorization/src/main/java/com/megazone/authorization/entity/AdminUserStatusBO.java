package com.megazone.authorization.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@ApiModel
@Builder
public class AdminUserStatusBO {

	@ApiModelProperty
	private List<Long> ids;

	@ApiModelProperty
	private Integer status;

}
