package com.megazone.admin.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class AdminUserBO extends PageEntity {

	@ApiModelProperty(value = "type", required = true, allowableValues = "0,1,2,3,4", example = "0")
	private Integer label;

	@ApiModelProperty
	private String realname;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private Integer roleId;

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty
	private List<Integer> deptIdList = new ArrayList<>();


	@ApiModelProperty(value = "User List")
	private List<Long> userIdList;

	private Long deptOwnerUserId;

	private Long userId;

	@ApiModelProperty(value = "Whether to show sub-department 0 not required 1 required")
	private Integer isNeedChild;
}
