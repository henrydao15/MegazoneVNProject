package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateScheduleBO {

	@ApiModelProperty(value = "id")
	private Integer segId;

	@ApiModelProperty(value = "")
	private Integer schedule;

	@ApiModelProperty(value = "")
	private String explainDesc;
}
