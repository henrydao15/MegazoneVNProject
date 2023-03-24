package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeArchivesFieldVO {

	@ApiModelProperty(value = "ID")
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " * 1  2 ")
	private Integer labelGroup;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isEmployeeVisible;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isEmployeeUpdate;
}
