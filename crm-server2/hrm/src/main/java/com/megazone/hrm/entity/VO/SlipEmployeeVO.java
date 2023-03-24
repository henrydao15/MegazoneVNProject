package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlipEmployeeVO {

	@ApiModelProperty(value = "id")
	private Integer sEmpRecordId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String jobNumber;

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty
	private String deptName;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty
	private String mobile;

	@ApiModelProperty(value = " 0  1 ")
	private Integer sendStatus;

}
