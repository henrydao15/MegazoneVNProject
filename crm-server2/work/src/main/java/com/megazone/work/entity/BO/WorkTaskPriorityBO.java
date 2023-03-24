package com.megazone.work.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel
public class WorkTaskPriorityBO {

	@ApiModelProperty
	private Integer taskId;

	@ApiModelProperty
	private Integer priority;
}
