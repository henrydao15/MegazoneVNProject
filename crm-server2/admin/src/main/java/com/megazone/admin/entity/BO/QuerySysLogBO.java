package com.megazone.admin.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuerySysLogBO extends PageEntity {

	@ApiModelProperty
	private String startTime;

	@ApiModelProperty
	private String endTime;

	@ApiModelProperty
	private String model;

	@ApiModelProperty
	private List<Integer> subModelLabels;

	@ApiModelProperty
	private List<Long> userIds;

	@ApiModelProperty
	private Integer type;
}
