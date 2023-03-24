package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class QueryMyAppraisalVO {

	@ApiModelProperty
	private Integer employeeAppraisalId;

	@ApiModelProperty
	private Integer appraisalId;

	@ApiModelProperty
	private String appraisalName;

	@ApiModelProperty
	private Date startTime;

	@ApiModelProperty
	private Date endTime;

	@ApiModelProperty
	private String appraisalTime;

	@ApiModelProperty
	private String followUpEmployeeName;

	@ApiModelProperty
	private BigDecimal score;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private String levelName;
	@ApiModelProperty
	private Integer isDraft;


}
