package com.megazone.oa.constart.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class TaskLabelBO {
	@ApiModelProperty(value = "task id")
	private Integer taskId;

	@ApiModelProperty(value = "tag id")
	private Integer labelId;

	@ApiModelProperty(value = "label name")
	private String labelName;

	@ApiModelProperty(value = "label color")
	private String color;
}
