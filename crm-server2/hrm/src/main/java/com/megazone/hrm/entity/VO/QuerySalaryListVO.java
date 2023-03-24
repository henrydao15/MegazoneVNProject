package com.megazone.hrm.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class QuerySalaryListVO {

	private Integer sEmpRecordId;

	@ApiModelProperty
	private Integer year;

	@ApiModelProperty
	private Integer month;

	@ApiModelProperty
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	@ApiModelProperty
	private String shouldSalary;

	@ApiModelProperty
	private String personalTax;

	@ApiModelProperty
	private String realSalary;

}
