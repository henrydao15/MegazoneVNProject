package com.megazone.admin.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@ApiModel
public class CloudConfigVO {

	@ApiModelProperty
	private String startTime;

	@ApiModelProperty
	private String endTime;

	@ApiModelProperty
	private Integer allNum;

	@ApiModelProperty
	private String createTime;

	@ApiModelProperty
	private Integer usingNum;
}
