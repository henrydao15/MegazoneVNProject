package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryOptionHeadVO {

	@ApiModelProperty
	private Integer code;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private Integer isFixed;

	@ApiModelProperty
	private String value;

	public SalaryOptionHeadVO(Integer code, String name, Integer isFixed) {
		this.code = code;
		this.name = name;
		this.isFixed = isFixed;
	}
}
