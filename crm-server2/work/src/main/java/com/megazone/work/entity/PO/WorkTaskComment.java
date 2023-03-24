package com.megazone.work.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.megazone.core.feign.admin.entity.SimpleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_work_task_comment")
@ApiModel(value = "WorkTaskComment", description = "")
public class WorkTaskComment implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	@TableId(value = "comment_id", type = IdType.AUTO)
	private Integer commentId;

	@ApiModelProperty(value = "ID")
	private Long userId;

	@ApiModelProperty(value = "()")
	private String content;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "id")
	private Integer mainId;

	@ApiModelProperty(value = "ID")
	private Long pid;

	@ApiModelProperty(value = " ")
	private Integer status;

	@ApiModelProperty(value = "ID ID")
	private Integer typeId;

	@ApiModelProperty(value = "")
	private Integer favour;

	@ApiModelProperty(value = " 1：  2：")
	private Integer type;


	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private SimpleUser user;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private SimpleUser replyUser;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private List<WorkTaskComment> childCommentList;


}
