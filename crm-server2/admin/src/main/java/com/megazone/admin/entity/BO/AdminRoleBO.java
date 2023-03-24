package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AdminRoleBO {

	@ApiModelProperty
	private List<Long> userIds;

	@ApiModelProperty
	private List<Integer> deptIds;

	@ApiModelProperty
	private List<Integer> roleIds;

}
