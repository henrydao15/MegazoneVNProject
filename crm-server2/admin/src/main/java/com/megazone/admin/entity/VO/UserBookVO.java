package com.megazone.admin.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserBookVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty
	private String realname;

	@ApiModelProperty
	private String img;

	@ApiModelProperty
	private String username;

	@ApiModelProperty(value = "User ID", required = true)
	private Long userId;

	@ApiModelProperty(value = "Gender, 0 unselected 1, male 2, female", required = true, allowableValues = "0,1,2")
	private Integer sex;

	@ApiModelProperty(value = "Mobile phone number")
	private String mobile;

	@ApiModelProperty(value = "mailbox")
	private String email;

	@ApiModelProperty(value = "Department ID")
	private Integer deptId;

	@ApiModelProperty(value = "Department Name")
	private String deptName;

	@ApiModelProperty(value = "Following status, 0 is not followed, 1 is followed")
	private Integer status;

	@ApiModelProperty(value = "status, 0 disabled, 1 normal, 2 inactive")
	private Integer userStatus;

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

	@ApiModelProperty(value = "role name")
	private String roleName;

	@ApiModelProperty
	private String initial;
}
