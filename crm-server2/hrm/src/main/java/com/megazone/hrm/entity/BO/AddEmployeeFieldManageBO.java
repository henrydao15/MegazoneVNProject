package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddEmployeeFieldManageBO {
	@ApiModelProperty
	private String jobNumber;
	@ApiModelProperty(value = "id")
	private Integer candidateId;
	@ApiModelProperty(value = "Id")
	private Integer employeeId;
	@ApiModelProperty(value = " 1  2  ")
	private Integer entryStatus;
	@ApiModelProperty
	private List<EmployeeFieldBO> employeeFieldList;
	@ApiModelProperty
	private List<EmployeeFieldBO> postFieldList;

	@Getter
	@Setter
	public static class EmployeeFieldBO {
		@ApiModelProperty(value = "ID")
		private Integer fieldId;

		@ApiModelProperty(value = " * 1  2  3  4  5 / 6  7  11  12  21  31  32 ")
		private Integer labelGroup;

		@ApiModelProperty(value = "")
		private String fieldName;

		@ApiModelProperty(value = "")
		private String name;

		@ApiModelProperty(value = "")
		private Object fieldValue;

		@ApiModelProperty(value = "")
		private String fieldValueDesc;
		@ApiModelProperty(value = " 1  0 ")
		private Integer isFixed;

		@ApiModelProperty(value = "")
		private Integer type;

	}
}
