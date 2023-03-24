package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuerySalaryArchivesListBO extends PageEntity {

	@ApiModelProperty(value = "")
	private String search;

	@ApiModelProperty
	private String post;

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty(value = " 1 2  3 4 5 6 7 8  11  12 ")
	private Integer status;

	@ApiModelProperty(value = " 0  1  2 ")
	private Integer changeType;
}
