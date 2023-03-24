package com.megazone.hrm.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class QueryInsurancePageListVO {
	@ApiModelProperty
	private Integer iEmpRecordId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String deptName;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty
	private String jobNumber;

	@ApiModelProperty
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date entryTime;

	@ApiModelProperty
	private String mobile;

	@ApiModelProperty
	private String city;

	@ApiModelProperty(value = "id")
	private Integer schemeId;

	@ApiModelProperty(value = "")
	private String schemeName;

	@ApiModelProperty(value = "")
	private BigDecimal personalInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal personalProvidentFundAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateProvidentFundAmount;

	private Integer isDel;
}
