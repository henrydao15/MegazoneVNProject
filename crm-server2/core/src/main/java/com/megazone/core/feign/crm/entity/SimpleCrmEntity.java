package com.megazone.core.feign.crm.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SimpleCrmEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty
	private Integer id;

	@ApiModelProperty
	private String name;
}
