package com.megazone.hrm.entity.BO;

import com.megazone.hrm.entity.VO.ChangeSalaryOptionVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetFixSalaryRecordBO {

	@ApiModelProperty
	private Integer employeeId;

	@ApiModelProperty
	private List<ChangeSalaryOptionVO> proSalary;

	@ApiModelProperty
	private String proSum;

	@ApiModelProperty
	private List<ChangeSalaryOptionVO> salary;

	@ApiModelProperty
	private String sum;

	@ApiModelProperty
	private String remarks;

	@ApiModelProperty
	private Integer id;
}
