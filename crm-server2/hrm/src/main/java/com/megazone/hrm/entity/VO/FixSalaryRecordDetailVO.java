package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FixSalaryRecordDetailVO extends SalaryRecordHeadVO {

	private Integer id;

	@ApiModelProperty(value = " 0  1  2 ")
	private Integer status;

	@ApiModelProperty
	private String remarks;

	@ApiModelProperty(value = " 0  1  2  3  4  5  6  7  8 ")
	private Integer changeReason;

	@ApiModelProperty
	private List<ChangeSalaryOptionVO> proSalary;

	@ApiModelProperty
	private List<ChangeSalaryOptionVO> salary;

	@ApiModelProperty
	private String proSum;

	@ApiModelProperty
	private String sum;

	@ApiModelProperty
	private Boolean isUpdate;


}
