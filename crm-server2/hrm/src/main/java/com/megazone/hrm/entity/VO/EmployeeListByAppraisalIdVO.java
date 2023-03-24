package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeListByAppraisalIdVO {

	@ApiModelProperty
	private Integer employeeId;

	@ApiModelProperty
	private Integer employeeAppraisalId;

	@ApiModelProperty
	private String employeeName;

	@ApiModelProperty
	private String mobile;

	@ApiModelProperty
	private String jobNumber;

	@ApiModelProperty
	private Integer employeeStatus;

	@ApiModelProperty
	private String deptName;

	@ApiModelProperty(value = " 1  2  3  4  5 ")
	private Integer status;

	@ApiModelProperty
	private String followUpEmployeeName;

	@ApiModelProperty(value = "")
	private BigDecimal score;

	@ApiModelProperty(value = "id")
	private Integer levelId;

	@ApiModelProperty(value = "")
	private String levelName;

	@ApiModelProperty(value = " 0  1 ")
	private Integer readStatus;

	private Integer isDel;

}
