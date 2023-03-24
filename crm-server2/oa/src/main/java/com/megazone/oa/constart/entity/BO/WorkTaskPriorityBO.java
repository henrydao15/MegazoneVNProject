package com.megazone.oa.constart.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WorkTaskPriorityBO {

	@ApiModelProperty
	private Integer taskId;

	@ApiModelProperty
	private Integer priority;
}
