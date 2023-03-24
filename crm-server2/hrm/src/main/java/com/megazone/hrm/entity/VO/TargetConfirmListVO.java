package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetConfirmListVO {

	@ApiModelProperty
	private Integer employeeAppraisalId;

	@ApiModelProperty
	private Integer appraisalId;

	@ApiModelProperty
	private Integer employeeId;

	@ApiModelProperty
	private String employeeName;

	@ApiModelProperty
	private String mobile;

	@ApiModelProperty
	private String deptName;

	@ApiModelProperty
	private String appraisalName;


}
