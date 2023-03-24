package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@ApiModel(value = "Department Editing Object", description = "Department Object")
public class AdminDeptBO {

	@ApiModelProperty(value = "Department ID")
	private Integer deptId;

	@ApiModelProperty(value = "Superior department ID, 0 is the highest level")
	@NotNull
	private Integer pid;

	@ApiModelProperty(value = "Department Name")
	@NotNull
	@Size(max = 20)
	private String name;

	@ApiModelProperty(value = "Department Head")
	private Long ownerUserId;
}
