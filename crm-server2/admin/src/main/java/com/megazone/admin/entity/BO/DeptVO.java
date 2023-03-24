package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptVO {


	@ApiModelProperty(value = "department id")
	private Integer deptId;

	@ApiModelProperty(value = "The parent ID top-level department is 0")
	private Integer pid;

	@ApiModelProperty(value = "Department Name")
	private String name;

	@ApiModelProperty
	private Integer currentNum;

	@ApiModelProperty
	private Integer allNum;

	@ApiModelProperty
	private Integer hasChildren;

	@ApiModelProperty(value = "Department Head")
	private Long ownerUserId;

}
