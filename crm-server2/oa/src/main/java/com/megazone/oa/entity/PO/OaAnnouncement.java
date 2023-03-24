package com.megazone.oa.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.feign.admin.entity.SimpleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_announcement")
@ApiModel(value = "OaAnnouncement object", description = "Announcement table")
public class OaAnnouncement implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "announcement_id", type = IdType.AUTO)
	private Integer announcementId;

	@ApiModelProperty(value = "title")
	private String title;

	@ApiModelProperty(value = "content")
	private String content;

	@ApiModelProperty(value = "Creator ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "update time")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "start time")
	private Date startTime;

	@ApiModelProperty(value = "end time")
	private Date endTime;

	@ApiModelProperty(value = "Notification Department")
	private String deptIds;

	@ApiModelProperty(value = "Notifier")
	private String ownerUserIds;

	@ApiModelProperty(value = "read users")
	private String readUserIds;


	@ApiModelProperty(value = "UserId")
	@TableField(exist = false)
	private Long userId;

	@ApiModelProperty(value = "Username")
	@TableField(exist = false)
	private String username;

	@ApiModelProperty(value = "User Avatar")
	@TableField(exist = false)
	private String img;

	@ApiModelProperty(value = "User Nickname")
	@TableField(exist = false)
	private String realname;

	@ApiModelProperty(value = "Parent ID")
	@TableField(exist = false)
	private Long parentId;

	@ApiModelProperty(value = "Is it read")
	@TableField(exist = false)
	private Integer isRead;

	@ApiModelProperty(value = "Notification Department")
	@TableField(exist = false)
	private List<SimpleDept> deptList;

	@ApiModelProperty(value = "Notifier")
	@TableField(exist = false)
	private List<SimpleUser> ownerUserList;

}
