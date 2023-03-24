package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateAppraisalStatusBO {

	@ApiModelProperty
	@NotNull
	private Integer appraisalId;

	@ApiModelProperty(value = "  1  2  3  4 ")
	@NotNull
	private Integer status;
}
