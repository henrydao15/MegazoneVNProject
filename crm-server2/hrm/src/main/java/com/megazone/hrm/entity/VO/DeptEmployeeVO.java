package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptEmployeeVO {


	@ApiModelProperty(value = "id")
	private Integer deptId;

	@ApiModelProperty(value = "ID 0")
	private Integer pid;

	@ApiModelProperty(value = "1  2 ")
	private Integer deptType;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String code;

	@ApiModelProperty
	private Integer allNum;

	@ApiModelProperty
	private Integer hasChildren;

	@ApiModelProperty
	private Integer currentNum;

}
