package com.megazone.examine.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ExamineBO {

	@ApiModelProperty
	private Integer recordId;

	@ApiModelProperty
	private Integer flowId;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private Integer typeId;

	@ApiModelProperty
	private String remarks;

	@ApiModelProperty
	private Long examineUserId;


}
