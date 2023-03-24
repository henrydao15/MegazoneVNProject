package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUniqueBO {

	@ApiModelProperty
	private Integer fieldId;

	@ApiModelProperty
	private String value;

	private Integer id;
	@ApiModelProperty
	private Integer status = 0;

}
