package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(value = "Department Editing Object", description = "Department Object")
public class AdminDeptQueryBO {

	@ApiParam(name = "id", value = "Parent ID", required = true, example = "0")
	private Integer id;

	@ApiModelProperty(value = "type")
	private String type;
}
