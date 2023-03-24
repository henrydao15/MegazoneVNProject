package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetConfirmBO {

	@ApiModelProperty
	private Integer employeeAppraisalId;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty(value = "")
	private String rejectReason;
}
