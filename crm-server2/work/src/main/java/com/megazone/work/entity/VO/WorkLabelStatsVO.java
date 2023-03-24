package com.megazone.work.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wyq
 */
@Data
@Accessors(chain = true)
@ApiModel
public class WorkLabelStatsVO {
	@ApiModelProperty
	private Integer labelId;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private String color;

	@ApiModelProperty
	private Integer complete;

	@ApiModelProperty
	private Integer undone;
}
