package com.megazone.admin.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@TableName("wk_sys_log")
@ApiModel(value = "SysLog object", description = "System log")
public class SysLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	@JsonIgnore
	private Long id;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
	private Date createTime;


}
