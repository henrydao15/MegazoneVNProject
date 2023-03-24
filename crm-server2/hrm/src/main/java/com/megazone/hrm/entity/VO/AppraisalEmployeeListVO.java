package com.megazone.hrm.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AppraisalEmployeeListVO {

	@ApiModelProperty
	private Integer employeeId;

	@ApiModelProperty
	private String employeeName;

	@ApiModelProperty
	private String mobile;

	@ApiModelProperty
	private String deptName;

	@ApiModelProperty
	private String post;

	@ApiModelProperty
	private Integer employeeStatus;


	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date becomeTime;

	@ApiModelProperty(value = "")
	private Integer apprailsaledCnt;

	@ApiModelProperty(value = "")
	private Integer apprailsalingCnt;

	@ApiModelProperty(value = "")
	private String lastAppraisalName;

	@ApiModelProperty(value = "")
	private String lastAppraisalResult;

	private Integer isDel;
}
