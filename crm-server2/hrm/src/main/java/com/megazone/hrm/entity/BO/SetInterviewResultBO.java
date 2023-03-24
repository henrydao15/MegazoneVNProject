package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetInterviewResultBO {
	@ApiModelProperty(value = "id")
	private Integer interviewId;

	@ApiModelProperty(value = "id")
	private Integer candidateId;

	@ApiModelProperty(value = "")
	private String remark;

	@ApiModelProperty(value = " 1 2 3 4 ")
	private Integer result;

	@ApiModelProperty(value = "")
	private String evaluate;

	@ApiModelProperty(value = "")
	private String cancelReason;
}
