package com.megazone.hrm.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class QuerySalaryArchivesListVO {

	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String jobNumber;

	@ApiModelProperty(value = "ID")
	private Integer deptId;

	@ApiModelProperty(value = "")
	private String deptName;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty(value = " 1 2  3 4 5 6 7 8 ")
	private Integer status;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date entryTime;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date becomeTime;

	@ApiModelProperty(value = " 1  2  3  4  5  6  7  8 ")
	private Integer changeReason;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date changeData;

	@ApiModelProperty(value = " 0  1  2 ")
	private Integer changeType;

	@ApiModelProperty(value = "")
	private String total;
}
