package com.megazone.work.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date 2020/11/6
 */
@Data
@ApiModel
public class WorkTaskQueryBO {

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private List<Long> userIdList;

	@ApiModelProperty
	private List<Integer> workIdList;

	@ApiModelProperty
	private List<Integer> labelIdList;

	@ApiModelProperty
	private Integer workSort;

	@ApiModelProperty
	private Integer sort;

	@ApiModelProperty
	private Integer type;

	@ApiModelProperty
	private String startTime;

	@ApiModelProperty
	private String endTime;
}
