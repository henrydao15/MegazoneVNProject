package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class SalaryOptionDetailVO {

	@ApiModelProperty
	private List<SalaryOptionVO> templateOption;

	@ApiModelProperty
	private List<SalaryOptionVO> openOption;
}
