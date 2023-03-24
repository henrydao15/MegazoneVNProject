package com.megazone.work.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
@TableName("wk_work_task_log")
@ApiModel(value = "WorkTaskLog", description = "")
public class WorkTaskLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	@TableId(value = "log_id", type = IdType.AUTO)
	private Integer logId;

	@ApiModelProperty(value = "ID")
	private Long userId;

	@ApiModelProperty(value = "")
	private String content;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = " 4")
	private Integer status;

	@ApiModelProperty(value = "ID")
	private Integer taskId;

	@ApiModelProperty(value = "ID")
	private Integer workId;


}
