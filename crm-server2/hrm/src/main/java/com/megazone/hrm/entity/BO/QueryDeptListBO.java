package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryDeptListBO {

	@ApiModelProperty
	private String name;

	@ApiModelProperty(value = " tree  update ")
	private String type;

	@ApiParam(name = "id", value = "ID", required = true, example = "0")
	private Integer id;
}
