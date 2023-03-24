package com.megazone.bi.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class AchievementBO {

	@ApiModelProperty
	private String year;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private Integer type;

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty
	private Long userId;
}
