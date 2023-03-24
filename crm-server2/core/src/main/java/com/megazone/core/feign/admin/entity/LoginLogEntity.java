package com.megazone.core.feign.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LoginLogEntity implements Serializable {

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
