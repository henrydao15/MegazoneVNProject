package com.megazone.oa.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventTaskVO {

	@ApiModelProperty
	private Integer taskId;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private Long startTime;

	@ApiModelProperty
	private Long endTime;

	@ApiModelProperty
	private Integer eventType;
}
