package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QueryEmployeeListByAppraisalIdBO extends PageEntity {

	@ApiModelProperty
	@NotNull
	private Integer appraisalId;

	@ApiModelProperty
	private String employeeName;

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty
	private Integer levelId;

	@ApiModelProperty
	private Integer status;

}
