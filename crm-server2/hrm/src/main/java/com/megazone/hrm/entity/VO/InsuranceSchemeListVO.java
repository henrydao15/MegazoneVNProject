package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InsuranceSchemeListVO {

	@ApiModelProperty(value = "id")
	private Integer schemeId;

	@ApiModelProperty(value = "")
	private String schemeName;

	@ApiModelProperty(value = "")
	private String city;

	@ApiModelProperty(value = " 1  2 ")
	private Integer schemeType;

	@ApiModelProperty
	private BigDecimal personalInsuranceAmount;

	@ApiModelProperty
	private BigDecimal personalProvidentFundAmount;

	@ApiModelProperty
	private BigDecimal corporateInsuranceAmount;

	@ApiModelProperty
	private BigDecimal corporateProvidentFundAmount;

	@ApiModelProperty
	private Integer useCount;


}
