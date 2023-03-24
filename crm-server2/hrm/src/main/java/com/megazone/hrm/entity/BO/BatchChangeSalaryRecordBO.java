package com.megazone.hrm.entity.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.hrm.entity.VO.ChangeSalaryOptionVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BatchChangeSalaryRecordBO {

	@ApiModelProperty
	private List<Integer> employeeIds;

	@ApiModelProperty
	private List<Integer> deptIds;

	@ApiModelProperty
	private Integer type;

	@ApiModelProperty
	private List<ChangeSalaryOptionVO> salaryOptions;

	@ApiModelProperty
	private String remarks;

	@ApiModelProperty(value = " 0  1  2  3  4  5  6  7  8 ")
	private Integer changeReason;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date enableDate;


}
