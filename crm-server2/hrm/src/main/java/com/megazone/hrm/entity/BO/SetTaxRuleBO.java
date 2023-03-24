package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetTaxRuleBO {

	@ApiModelProperty
	private Integer ruleId;

	@ApiModelProperty(value = " 1 1211（） 2 112（）")
	private Integer cycleType;
}
