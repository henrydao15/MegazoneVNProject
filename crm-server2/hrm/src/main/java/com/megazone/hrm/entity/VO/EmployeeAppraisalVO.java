package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ApiModel
public class EmployeeAppraisalVO {

	@ApiModelProperty
	private Integer employeeId;

	@ApiModelProperty
	private String employeeAppraisalId;

	@ApiModelProperty
	private BigDecimal score;
//    private String score;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private String appraisalName;

	@ApiModelProperty(value = "")
	private Date startTime;

	@ApiModelProperty(value = "")
	private Date endTime;

	@ApiModelProperty(value = "")
	private String resultLevel;

	@ApiModelProperty(value = "")
	private String appraisalTime;

}
