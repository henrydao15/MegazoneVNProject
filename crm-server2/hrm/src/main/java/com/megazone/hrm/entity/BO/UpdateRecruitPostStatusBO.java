package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRecruitPostStatusBO {

	@ApiModelProperty(value = "id")
	private Integer postId;

	@ApiModelProperty(value = "0   1 ")
	private Integer status;

	@ApiModelProperty(value = "")
	private String reason;
}
