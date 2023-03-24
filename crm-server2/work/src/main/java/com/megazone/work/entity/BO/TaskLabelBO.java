package com.megazone.work.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel
public class TaskLabelBO {
	@ApiModelProperty(value = "id")
	private Integer taskId;

	@ApiModelProperty(value = "id")
	private Integer labelId;

	@ApiModelProperty(value = "")
	private String labelName;

	@ApiModelProperty(value = "")
	private String color;
}
