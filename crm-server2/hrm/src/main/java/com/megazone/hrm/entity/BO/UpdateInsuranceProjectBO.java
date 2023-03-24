package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UpdateInsuranceProjectBO {

	@ApiModelProperty
	private Integer iEmpRecordId;

	@ApiModelProperty
	private List<Integer> iEmpRecordIds;

	@ApiModelProperty
	private Integer schemeId;

	@ApiModelProperty
	private List<Project> projectList;

	@Getter
	@Setter
	public static class Project {
		@ApiModelProperty
		private Integer projectId;

		@ApiModelProperty(value = "")
		private BigDecimal defaultAmount;

		@ApiModelProperty(value = "")
		private BigDecimal corporateAmount;

		@ApiModelProperty(value = "")
		private BigDecimal personalAmount;

	}
}
