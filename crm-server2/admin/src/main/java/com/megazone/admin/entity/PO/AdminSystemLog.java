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
@TableName("wk_admin_system_log")
@ApiModel(value = "AdminSystemLog object", description = "System log table")
public class AdminSystemLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "operator id")
	@TableField(fill = FieldFill.INSERT)
	private Integer createUserId;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "IP address")
	private String ipAddress;

	@ApiModelProperty(value = "Module 1 Enterprise home page 2 Application management 3 Employee and department management 4 Business card applet management 5 Role rights management 6 Approval flow (contract/payment) 7 Approval flow (office) 8 Project management 9 Customer management 10 System Log Management 11 Other Settings")
	private Integer types;

	@ApiModelProperty(value = "behavior")
	private Integer behavior;

	@ApiModelProperty(value = "Operation object")
	private String object;

	@ApiModelProperty(value = "operation details")
	private String detail;
}
