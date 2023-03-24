package com.megazone.hrm.entity.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ApiModel(description = "")
public class QueryEmployeePageListBO extends PageEntity {

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String mobile;

	@ApiModelProperty(value = " 1  2 ")
	private Integer sex;

	@ApiModelProperty(value = "")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private List<Date> entryTime;

	@ApiModelProperty(value = "")
	private String jobNumber;

	@ApiModelProperty(value = "ID")
	private Integer deptId;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty(value = "")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private List<Date> becomeTime;

	@ApiModelProperty(value = "")
	private String workAddress;

	@ApiModelProperty(value = " 1  2 ")
	private Integer employmentForms;

	@ApiModelProperty(value = " 1 2  3 4 5 6 7 8 9 10 11  12 ")
	private Integer status;

	@ApiModelProperty
	private Integer employeeSurvey;

	@ApiModelProperty
	private Integer toDoRemind;

	@ApiModelProperty
	private List<Integer> employeeIds;

	@ApiModelProperty(value = "")
	private String sortField;
	@ApiModelProperty(value = " 1  2 ")
	private Integer order;


}
