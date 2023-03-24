package com.megazone.admin.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AdminMessageQueryBO extends PageEntity {

	@ApiModelProperty
	private Integer isRead;

	@ApiModelProperty
	private Integer label;

	@ApiModelProperty
	private Long userId;

	@ApiModelProperty
	private Integer type;
}
