package com.megazone.hrm.entity.VO;

import com.megazone.core.feign.admin.entity.AdminRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeInfo {


	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String mobile;

	@ApiModelProperty(value = " 1  2 ")
	private Integer sex;

	@ApiModelProperty(value = "")
	private Integer age;

	@ApiModelProperty(value = "")
	private String jobNumber;

	@ApiModelProperty(value = "ID")
	private Integer deptId;

	@ApiModelProperty(value = "")
	private String deptName;

	@ApiModelProperty(value = "ID")
	private Integer parentId;

	@ApiModelProperty(value = "")
	private Integer parentName;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty
	private AdminRole role;
}
