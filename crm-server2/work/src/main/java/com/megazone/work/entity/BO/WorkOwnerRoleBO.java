package com.megazone.work.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wyq
 */
@Data
@ApiModel
public class WorkOwnerRoleBO {
	@ApiModelProperty
	private Long userId;

	@ApiModelProperty
	private String realname;

	@ApiModelProperty
	private String img;

	@ApiModelProperty
	private Integer roleId;

	@ApiModelProperty
	private String roleName;
}
