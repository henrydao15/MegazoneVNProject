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
@TableName("wk_admin_login_log")
@ApiModel(value = "AdminLoginLog object", description = "System login log table")
public class LoginLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "operator id")
	@TableField(fill = FieldFill.INSERT)
	private Long userId;

	@ApiModelProperty(value = "operator")
	private String realname;

	@ApiModelProperty(value = "login time")
	private Date loginTime;

	@ApiModelProperty(value = "login ip address")
	private String ipAddress;

	@ApiModelProperty(value = "Login Location")
	private String loginAddress;

	@ApiModelProperty(value = "device type")
	private String deviceType;

	@ApiModelProperty(value = "terminal kernel")
	private String core;

	@ApiModelProperty(value = "platform")
	private String platform;

	@ApiModelProperty(value = "Authentication result 1 success 2 failure")
	private Integer authResult;

	@ApiModelProperty(value = "Failure result")
	private String failResult;


}
