package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeFieldManageVO {
	@ApiModelProperty(value = "id")
	private Integer id;

	@ApiModelProperty(value = " 1  2  ")
	private Integer entryStatus;

	@ApiModelProperty(value = "id")
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String fieldName;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1  2  3  4 ")
	private Integer label;

	@ApiModelProperty(value = " * 1  2  3  4  5 / 6  7  11  12  21  31  32 ")
	private Integer labelGroup;

	@ApiModelProperty(value = " 0  1   2  3 ")
	private Integer isManageVisible;
}
