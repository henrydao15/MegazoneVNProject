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
	private Date createTime;

	@ApiModelProperty(value = "Whether read 0 unread 1 read")
	private Integer isRead;

	@ApiModelProperty(value = "read time")
	private Date readTime;
	@ApiModelProperty(value = "valid")
	@TableField(exist = false)
	private Integer valid;

	@ApiModelProperty(value = "nickname")
	@TableField(exist = false)
	private String realname;

	public AdminMessage() {
		/* When constructing a message object, it is unread by default to solve the problem of batch saving errors */
		this.isRead = 0;
	}
}
