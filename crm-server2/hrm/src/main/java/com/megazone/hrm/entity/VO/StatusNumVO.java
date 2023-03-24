package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(description = "")
public class StatusNumVO {

	@ApiModelProperty
	private String label;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private Integer count;
}
