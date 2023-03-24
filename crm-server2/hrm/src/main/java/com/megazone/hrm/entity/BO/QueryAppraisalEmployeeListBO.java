package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryAppraisalEmployeeListBO extends PageEntity {

	@ApiModelProperty
	private String employeeName;

	@ApiModelProperty
	private String mobile;

	@ApiModelProperty
	private Integer employeeStatus;

	@ApiModelProperty
	private Integer deptId;
}
