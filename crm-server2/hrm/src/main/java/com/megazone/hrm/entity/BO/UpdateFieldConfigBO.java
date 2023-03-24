package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateFieldConfigBO {

	@ApiModelProperty(value = "id")
	private Integer id;

	@ApiModelProperty(value = "")
	private Integer sort;

	@ApiModelProperty(value = "id")
	private Long userId;

	@ApiModelProperty(value = " 0、 1、")
	private Integer isHide;

	@ApiModelProperty(value = "")
	private Integer width;
}
