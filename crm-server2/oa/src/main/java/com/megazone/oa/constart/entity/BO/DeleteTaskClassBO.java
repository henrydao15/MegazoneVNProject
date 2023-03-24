package com.megazone.oa.constart.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class DeleteTaskClassBO {
	@ApiModelProperty
	@NotNull
	private Integer workId;

	@ApiModelProperty
	@NotNull
	private Integer classId;
}
