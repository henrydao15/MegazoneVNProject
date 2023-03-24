package com.megazone.oa.entity.PO;

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
@TableName("wk_oa_examine_log")
@ApiModel(value = "OaExamineLog object", description = "Audit log table")
public class OaExamineLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "log_id", type = IdType.AUTO)
	private Long logId;

	@ApiModelProperty(value = "Approval Record ID")
	private Integer recordId;

	@ApiModelProperty(value = "Audit Step ID")
	private Integer examineStepId;

	@ApiModelProperty(value = "Audit Status 0 Not Approved 1 Approved 2 Approved Rejected 4 Withdrawn Approval")
	private Integer examineStatus;

	@ApiModelProperty(value = "Creator")
	private Long createUser;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "Reviewer")
	private Long examineUser;

	@ApiModelProperty(value = "Audit Time")
	private Date examineTime;

	@ApiModelProperty(value = "Audit Notes")
	private String remarks;

	@ApiModelProperty(value = "Whether it is the log before withdrawal 0 or null for new data 1: withdraw the previous data")
	private Integer isRecheck;

	@ApiModelProperty(value = "sort id")
	private Integer orderId;


}
