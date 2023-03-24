package com.megazone.core.feign.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * System message table
 * </p>
 *
 * @author
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_admin_message")
@ApiModel(value = "AdminMessage object", description = "System message table")
public class AdminMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Message ID")
	@TableId(value = "message_id", type = IdType.AUTO)
	private Long messageId;

	@ApiModelProperty(value = "Message Title")
	private String title;

	@ApiModelProperty(value = "content")
	private String content;

	@ApiModelProperty(value = "Message category 1 task 2 log 3 oa approval 4 announcement 5 schedule 6 crm message")
	private Integer label;

	@ApiModelProperty(value = "Message type see AdminMessageEnum")
	private Integer type;

	@ApiModelProperty(value = "Association ID")
	private Integer typeId;

	@ApiModelProperty(value = "Message creator 0 is the system")
	private Long createUser;

	@ApiModelProperty(value = "Receiver")
	private Long recipientUser;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private String createTime;

	@ApiModelProperty(value = "Whether read 0 unread 1 read")
	private Integer isRead;

	@ApiModelProperty(value = "read time")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime readTime;


}
