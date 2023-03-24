package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QueryChangeOptionValueVO {

	@ApiModelProperty
	private List<ChangeSalaryOptionVO> proSalary;

	@ApiModelProperty
	private List<ChangeSalaryOptionVO> salary;
}
