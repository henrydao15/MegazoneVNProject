package com.megazone.hrm.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChangeSalaryRecordDetailVO extends SalaryRecordHeadVO {

	private Integer id;

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

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date enableDate;

	@ApiModelProperty(value = " 0  1  2 ")
	private Integer status;

	@ApiModelProperty(value = " 0  1  2  3  4  5  6  7  8 ")
	private Integer changeReason;

	@ApiModelProperty
	private Boolean isUpdate;

	@ApiModelProperty
	private Boolean isCancel;

	@ApiModelProperty
	private Boolean isDelete;


}
