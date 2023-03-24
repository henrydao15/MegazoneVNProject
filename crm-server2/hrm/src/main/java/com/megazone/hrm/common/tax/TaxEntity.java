package com.megazone.hrm.common.tax;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TaxEntity {
	@ApiModelProperty
	private Integer taxRate;

	@ApiModelProperty
	private Integer quickDeduction;
}
