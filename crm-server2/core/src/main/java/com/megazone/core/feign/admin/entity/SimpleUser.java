package com.megazone.core.feign.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SimpleUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty
	private Long userId;

	@ApiModelProperty
	private String img;

	@ApiModelProperty
	private String realname;

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty
	private String deptName;

	public SimpleUser() {
	}

	public SimpleUser(Long userId, String img, String realname, Integer deptId, String deptName) {
		this.userId = userId;
		this.img = img;
		this.realname = realname;
		this.deptId = deptId;
		this.deptName = deptName;
	}
}
