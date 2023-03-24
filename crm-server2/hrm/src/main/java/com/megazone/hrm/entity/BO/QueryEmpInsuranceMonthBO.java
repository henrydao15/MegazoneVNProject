package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryEmpInsuranceMonthBO extends PageEntity {

	@ApiModelProperty
	private Integer year;

	@ApiModelProperty
	private Integer month;
}
