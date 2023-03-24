package com.megazone.oa.constart.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WorkTaskLabelBO {

	@ApiModelProperty
	private Integer labelId;

	@ApiModelProperty
	private Integer taskId;

	@ApiModelProperty
	private String labelName;

	@ApiModelProperty
	private String color;
}
