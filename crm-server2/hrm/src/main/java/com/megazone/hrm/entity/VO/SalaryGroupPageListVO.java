package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class SalaryGroupPageListVO {

	@ApiModelProperty(value = "id")
	private Integer groupId;

	@ApiModelProperty(value = "")
	private String groupName;

	@ApiModelProperty(value = "")
	private List<SimpleHrmDeptVO> deptList;

	@ApiModelProperty(value = "")
	private List<SimpleHrmEmployeeVO> employeeList;

	@ApiModelProperty(value = "")
	private String salaryStandard;

	@ApiModelProperty(value = "、")
	private String changeRule;

	@ApiModelProperty(value = "id")
	private Integer ruleId;

	@ApiModelProperty(value = "")
	private String ruleName;

}
