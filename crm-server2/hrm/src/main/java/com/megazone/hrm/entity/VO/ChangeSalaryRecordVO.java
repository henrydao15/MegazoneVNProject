package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChangeSalaryRecordVO {
	@ApiModelProperty
	private List<ChangeSalaryOptionVO> oldSalary;

	@ApiModelProperty
	private List<ChangeSalaryOptionVO> newSalary;
}
