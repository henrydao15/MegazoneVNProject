package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class EmpInsuranceByIdVO {

	@ApiModelProperty
	private String city;

	@ApiModelProperty
	private String idNumber;

	@ApiModelProperty
	private String socialSecurityNum;

	@ApiModelProperty
	private String accumulationFundNum;

	@ApiModelProperty
	private Integer schemeId;

	@ApiModelProperty
	private String schemeName;

	@ApiModelProperty(value = " 1  2 ")
	private Integer schemeType;

	private List<HrmInsuranceProjectBO> socialSecurityList;
	private List<HrmInsuranceProjectBO> providentFundList;


	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class HrmInsuranceProjectBO {

		private Integer empProjectRecordId;

		@ApiModelProperty(value = "id")
		private Integer projectId;

		@ApiModelProperty(value = "1  2  3  4  5  6  7  8  9  10  11 ")
		private Integer type;

		@ApiModelProperty(value = "")
		private String projectName;

		@ApiModelProperty(value = "")
		private BigDecimal defaultAmount;

		@ApiModelProperty(value = "")
		private BigDecimal corporateProportion;

		@ApiModelProperty(value = "")
		private BigDecimal personalProportion;

		@ApiModelProperty(value = "")
		private BigDecimal corporateAmount;

		@ApiModelProperty(value = "")
		private BigDecimal personalAmount;
	}
}
