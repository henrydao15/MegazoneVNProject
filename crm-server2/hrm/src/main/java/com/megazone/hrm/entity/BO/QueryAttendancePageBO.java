package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryAttendancePageBO extends PageEntity {

	@ApiModelProperty
	private String date;

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty
	private String search;
}
