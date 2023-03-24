package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AddInsuranceSchemeBO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Integer schemeId;

	@ApiModelProperty(value = "")
	@NotBlank(message = "")
	private String schemeName;

	@ApiModelProperty(value = "")
	private String city;

	@ApiModelProperty(value = "")
	private String houseType;

	@ApiModelProperty(value = " 1  2 ")
	private Integer schemeType;

	@ApiModelProperty
	private List<HrmInsuranceProjectBO> socialSecurityProjectList;

	@ApiModelProperty
	private List<HrmInsuranceProjectBO> providentFundProjectList;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class HrmInsuranceProjectBO {

		@ApiModelProperty(value = "id")
		private Integer projectId;

		@ApiModelProperty(value = "1  2  3  4  5  6  7  8  9  10  11 ")
		private Integer type;

		@ApiModelProperty(value = "")
		private String projectName;

		@ApiModelProperty(value = "")
		@DecimalMin(value = "0", message = "")
		private BigDecimal defaultAmount;

		@ApiModelProperty(value = "")
		@DecimalMin(value = "0", message = "")
		private BigDecimal corporateProportion;

		@ApiModelProperty(value = "")
		@DecimalMin(value = "0", message = "")
		private BigDecimal personalProportion;

		@ApiModelProperty(value = "")
		@DecimalMin(value = "0", message = "")
		private BigDecimal corporateAmount;

		@ApiModelProperty(value = "")
		@DecimalMin(value = "0", message = "")
		private BigDecimal personalAmount;
	}
}
