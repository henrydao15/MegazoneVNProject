package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateFieldWidthBO {

	@ApiModelProperty
	private Integer fieldId;

	@ApiModelProperty
	private Integer width;
}
