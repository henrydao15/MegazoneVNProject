package com.megazone.hrm.entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class ComputeSalaryDTO {

	private Integer id;

	@ApiModelProperty
	private Integer code;

	@ApiModelProperty
	private Integer parentCode;

	@ApiModelProperty
	private String value;

	@ApiModelProperty
	private Integer isPlus;

	@ApiModelProperty
	private Integer isTax;

	@ApiModelProperty
	private Integer isFixed;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private Integer isCompute;


}
