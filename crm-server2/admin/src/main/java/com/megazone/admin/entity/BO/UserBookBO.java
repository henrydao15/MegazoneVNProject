package com.megazone.admin.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserBookBO extends PageEntity {

	@ApiModelProperty
	private String search;

	@ApiModelProperty
	private Integer initial;

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty(value = "Following status, 0 is not followed, 1 is followed")
	private Integer status;

	@ApiModelProperty(value = "User ID")
	private Long userId;

}
