package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuerySlipEmployeePageListBO extends PageEntity {

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty(value = " 0  1 ")
	private Integer sendStatus;

}
