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
public class WorkTaskStatsVO {

	@ApiModelProperty
	private Integer allCount;

	@ApiModelProperty
	private Integer complete;

	@ApiModelProperty
	private String completionRate;

	@ApiModelProperty
	private Integer unfinished;

	@ApiModelProperty
	private Integer overdue;

	@ApiModelProperty
	private String overdueRate;

	@ApiModelProperty
	private Integer archive;
}
