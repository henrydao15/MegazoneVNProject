package com.megazone.hrm.entity.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.hrm.entity.VO.ChangeSalaryRecordVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SetChangeSalaryRecordBO {

	@ApiModelProperty
	private Integer employeeId;

	@ApiModelProperty
	private ChangeSalaryRecordVO proSalary;

	@ApiModelProperty(value = "")
	private String proBeforeSum;

	@ApiModelProperty(value = "")
	private String proAfterSum;

	@ApiModelProperty
	private ChangeSalaryRecordVO salary;

	@ApiModelProperty(value = "")
	private String beforeSum;

	@ApiModelProperty(value = "")
	private String afterSum;

	@ApiModelProperty
	private String remarks;

	@ApiModelProperty(value = " 0  1  2  3  4  5  6  7  8 ")
	private Integer changeReason;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date enableDate;

	@ApiModelProperty
	private Integer id;
}
