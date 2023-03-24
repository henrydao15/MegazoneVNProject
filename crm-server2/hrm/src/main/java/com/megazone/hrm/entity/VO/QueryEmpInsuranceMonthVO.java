package com.megazone.hrm.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class QueryEmpInsuranceMonthVO {
	@ApiModelProperty(value = "id")
	@TableId(value = "employee_id", type = IdType.AUTO)
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String mobile;

	@ApiModelProperty(value = "")
	private Date entryTime;

	@ApiModelProperty
	private String deptName;

	@ApiModelProperty
	private String city;

	@ApiModelProperty
	private String schemeName;

	@ApiModelProperty(value = "")
	private BigDecimal personalInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal personalProvidentFundAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateProvidentFundAmount;
}
