package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ResultEvaluatoBO {

	@ApiModelProperty(value = "id")
	private Integer employeeAppraisalId;

	@ApiModelProperty(value = "")
	private BigDecimal score;

	@ApiModelProperty(value = "")
	private Integer levelId;

	@ApiModelProperty(value = "")
	private String evaluate;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty(value = "")
	private String rejectReason;

	@ApiModelProperty
	private List<ResultEvaluatoSegBO> resultEvaluatoSegBOList;

	@Getter
	@Setter
	public static class ResultEvaluatoSegBO {
		@ApiModelProperty(value = "id")
		private Integer segId;

		@ApiModelProperty(value = "")
		private BigDecimal score;

		@ApiModelProperty(value = "")
		private String evaluate;
	}
}
