package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateScoreLevelBO {

	@ApiModelProperty(value = "id")
	private Integer employeeAppraisalId;

	@ApiModelProperty(value = "")
	private BigDecimal score;

	@ApiModelProperty(value = "")
	private Integer levelId;

	@ApiModelProperty(value = "")
	private String reason;
}
