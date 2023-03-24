package com.megazone.work.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel
public class WorkTaskNameBO {

	@ApiModelProperty
	private Integer taskId;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private Integer sort;

	@ApiModelProperty
	private Boolean completedTask;

	@ApiModelProperty
	private List<Integer> userIdList;

	@ApiModelProperty
	private Integer stopTimeType;

	@ApiModelProperty
	private List<Integer> workIdList;

	@ApiModelProperty
	private List<Integer> labelIdList;
}
