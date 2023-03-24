package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MySurveyVO {

	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String mobile;

	@ApiModelProperty(value = " 1  2 ")
	private Integer sex;

	@ApiModelProperty(value = "")
	private Integer age;

	@ApiModelProperty(value = "")
	private String jobNumber;

	@ApiModelProperty(value = "ID")
	private Integer deptId;

	@ApiModelProperty(value = "")
	private String deptName;

	@ApiModelProperty(value = "ID")
	private Integer parentId;

	@ApiModelProperty(value = "")
	private Integer parentName;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty
	private Date entryTime;

	@ApiModelProperty
	private Date becomeTime;

	@ApiModelProperty
	private String img;

	@ApiModelProperty
	private String companyName;

	@ApiModelProperty
	private Long entryDay;

	@ApiModelProperty
	private String slipRemarks;
}
