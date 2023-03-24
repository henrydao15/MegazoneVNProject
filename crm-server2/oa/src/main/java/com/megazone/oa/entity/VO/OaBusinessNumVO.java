package com.megazone.oa.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class OaBusinessNumVO {

	@ApiModelProperty
	private Integer logNum;

	@ApiModelProperty
	private Integer examineNum;

	@ApiModelProperty
	private Integer taskNum;

	@ApiModelProperty
	private Integer activityNum;
}
