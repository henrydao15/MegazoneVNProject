package com.megazone.work.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel
public class WorkUserStatsVO {
	@ApiModelProperty
	private Long userId;

	@ApiModelProperty
	private String realname;

	@ApiModelProperty
	private String img;

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
	private Integer overdueRate;

	@ApiModelProperty
	private Integer archive;
}
