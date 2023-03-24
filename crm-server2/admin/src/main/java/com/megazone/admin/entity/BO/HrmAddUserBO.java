package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HrmAddUserBO {

	@ApiModelProperty
	private String password;

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty
	private String roleId;

	@ApiModelProperty
	private Long parentId;

	@ApiModelProperty
	private List<Integer> employeeIds;


}
