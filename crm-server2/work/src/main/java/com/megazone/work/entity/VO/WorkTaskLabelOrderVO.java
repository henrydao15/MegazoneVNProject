package com.megazone.work.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel
public class WorkTaskLabelOrderVO {
	@ApiModelProperty
	private Integer labelId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private Integer orderNum;

	@ApiModelProperty(value = "")
	private String color;
}
