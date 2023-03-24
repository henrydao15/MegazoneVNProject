package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmQueryExamineStepBO {

	@ApiModelProperty
	private Integer id;

	@ApiModelProperty
	private Integer categoryType;
}
