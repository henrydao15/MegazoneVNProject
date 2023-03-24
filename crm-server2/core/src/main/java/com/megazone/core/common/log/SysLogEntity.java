package com.megazone.core.common.log;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SysLogEntity {

	@ApiModelProperty(value = "class name")
	private String className;

	@ApiModelProperty(value = "method name")
	private String methodName;

	@ApiModelProperty(value = "parameter")
	private String args;

	@ApiModelProperty(value = "module name")
	private String model;

	@ApiModelProperty(value = "Submodule name (lead, customer...)")
	private Integer subModelLabel;

	@ApiModelProperty(value = "Submodule name (lead, customer...)")
	private String subModel;

	@ApiModelProperty(value = "object")
	private String object;

	@ApiModelProperty(value = "behavior")
	private String behavior;

	@ApiModelProperty(value = "operation details")
	private String detail;

	@ApiModelProperty(value = "ip address")
	private String ipAddress;

	private Long userId;

	@ApiModelProperty(value = "operator name")
	private String realname;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

}
