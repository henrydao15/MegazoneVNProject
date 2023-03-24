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
@TableName("wk_oa_log")
@ApiModel(value = "OaLog object", description = "Work log table")
public class OaLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "log_id", type = IdType.AUTO)
	private Integer logId;

	@ApiModelProperty(value = "Log type (1 daily, 2 weekly, 3 monthly)")
	private Integer categoryId;

	@ApiModelProperty(value = "Log Title")
	private String title;

	@ApiModelProperty(value = "Log content")
	private String content;

	@ApiModelProperty(value = "Tomorrow's work content")
	private String tomorrow;

	@ApiModelProperty(value = "Problem encountered")
	private String question;

	@ApiModelProperty(value = "Creator ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "update time")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "Notifier")
	private String sendUserIds;

	@ApiModelProperty(value = "Notification Department")
	private String sendDeptIds;

	@ApiModelProperty(value = "read people")
	private String readUserIds;

	@ApiModelProperty(value = "File batch ID")
	private String batchId;


}
