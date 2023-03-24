package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDeleteByBatchIdBO {

	private String batchId;

	@ApiModelProperty
	private Integer type;
}
