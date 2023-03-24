package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "")
public class SalaryRecordHeadVO {

	@ApiModelProperty
	private Integer employeeId;

	@ApiModelProperty
	private String employeeName;

	@ApiModelProperty
	private String deptName;

	@ApiModelProperty
	private String post;

	@ApiModelProperty(value = " 1 2  3 4 5 6 7 8")
	private Integer employeeStatus;


}
