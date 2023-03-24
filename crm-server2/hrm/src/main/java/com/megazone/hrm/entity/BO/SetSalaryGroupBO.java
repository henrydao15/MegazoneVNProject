package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetSalaryGroupBO {
	@ApiModelProperty(value = "id")
	private Integer groupId;

	@ApiModelProperty(value = "")
	private String groupName;

	@ApiModelProperty(value = "")
	private List<Integer> deptIds;

	@ApiModelProperty(value = "")
	private List<Integer> employeeIds;

	@ApiModelProperty(value = "id")
	private Integer ruleId;
}
