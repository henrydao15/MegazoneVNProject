package com.megazone.admin.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@ApiModel
public class AdminUserVO {

	@ApiModelProperty
	private String realname;

	@ApiModelProperty
	private String username;

	@ApiModelProperty(value = "User ID", required = true)
	private Long userId;

	@ApiModelProperty(value = "Gender, 0 unselected 1, male 2, female", required = true, allowableValues = "0,1,2")
	private Integer sex;

	@ApiModelProperty(value = "Mobile phone number")
	private String mobile;

	@ApiModelProperty(value = "password")
	private String password;

	@ApiModelProperty(value = "mailbox")
	private String email;

	@ApiModelProperty(value = "Department ID")
	private Integer deptId;

	@ApiModelProperty(value = "Department Name")
	private String deptName;

	@ApiModelProperty(value = "status, 0 disabled, 1 normal, 2 inactive")
	private Integer status;

	@ApiModelProperty(value = "creation time")
	private Date createTime;

	@ApiModelProperty(value = "post")
	private String post;

	@ApiModelProperty(value = "Superior ID")
	private Long parentId;

	@ApiModelProperty(value = "superior name")
	private String parentName;

	@ApiModelProperty(value = "role ID")
	private String roleId;

	@ApiModelProperty(value = "role ID")
	private String roleIds;

	@ApiModelProperty(value = "role name")
	private String roleName;

	@ApiModelProperty(value = "User Avatar")
	private String img;

	@ApiModelProperty(value = "Is it a super administrator")
	private Boolean isAdmin;

	private Integer isReadNotice;

	private Integer emailId;

	@ApiModelProperty
	private Integer userIdentity = 2;

	private Integer hisTable;
}
