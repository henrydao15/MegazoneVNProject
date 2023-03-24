package com.megazone.oa.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteEventBO {
	@ApiModelProperty
	private Integer type;
	@ApiModelProperty
	private Long time;
	@ApiModelProperty
	private Integer eventId;
	@ApiModelProperty
	private String batchId;
}
