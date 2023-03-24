package com.megazone.core.feign.hrm.entity;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryEmployeeListBO extends PageEntity {
	@ApiModelProperty
	private String search;

	@ApiModelProperty
	private Integer deptId;

}
