package com.megazone.work.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel
public class WorkTaskUserBO {

	@ApiModelProperty
	private Integer taskId;

	@ApiModelProperty
	private Long userId;
}
