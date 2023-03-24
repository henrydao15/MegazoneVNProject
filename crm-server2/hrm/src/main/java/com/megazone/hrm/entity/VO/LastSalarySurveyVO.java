package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class LastSalarySurveyVO {

	@ApiModelProperty
	private Integer sRecordId;

	@ApiModelProperty
	private Integer total;

	@ApiModelProperty
	private BigDecimal totalSalary;

	@ApiModelProperty
	private List<DeptProportion> deptProportionList;

	@Getter
	@Setter
	public static class DeptProportion {

		@ApiModelProperty
		private Integer deptId;

		@ApiModelProperty
		private String deptName;

		@ApiModelProperty
		private BigDecimal proportion;

		@ApiModelProperty
		private BigDecimal totalSalary;
	}
}
