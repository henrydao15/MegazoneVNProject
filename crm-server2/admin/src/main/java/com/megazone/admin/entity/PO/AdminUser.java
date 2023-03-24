package com.megazone.admin.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_admin_user")
@ApiModel(value = "AdminUser object", description = "User table")
public class AdminUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "primary key")
	@TableId(value = "user_id", type = IdType.AUTO)
	private Long userId;

	@ApiModelProperty(value = "Username")
	private String username;

	@ApiModelProperty(value = "password")
	private String password;

	@ApiModelProperty(value = "safety")
	private String salt;

	@ApiModelProperty(value = "avatar")
	private String img;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "real name")
	private String realname;

	@ApiModelProperty(value = "employee number")
	private String num;

	@ApiModelProperty(value = "Mobile phone number")
	private String mobile;

	@ApiModelProperty(value = "mailbox")
	private String email;

	@ApiModelProperty(value = "0 1 male 2 female not selected")
	private Integer sex;

	@ApiModelProperty(value = "Department")
	private Integer deptId;

	@ApiModelProperty(value = "Department Name")
	@TableField(exist = false)
	private String deptName;

	@ApiModelProperty(value = "post")
	private String post;

	@ApiModelProperty(value = "status, 0 disabled, 1 normal, 2 inactive")
	private Integer status;

	@ApiModelProperty(value = "direct superior ID")
	private Long parentId;

	@ApiModelProperty(value = "Last login time")
	private Date lastLoginTime;

	@ApiModelProperty(value = "The last login IP is compatible with IPV6")
	private String lastLoginIp;


	@TableField(exist = false)
	private String companyName;

	@ApiModelProperty
	private Integer isDel;


}
