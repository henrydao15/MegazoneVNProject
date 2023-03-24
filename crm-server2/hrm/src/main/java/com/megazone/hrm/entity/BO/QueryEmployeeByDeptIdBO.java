package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryEmployeeByDeptIdBO extends PageEntity {

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty
	private String search;
}
