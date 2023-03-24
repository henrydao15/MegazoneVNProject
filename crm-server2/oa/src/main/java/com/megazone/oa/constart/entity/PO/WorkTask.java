package com.megazone.oa.constart.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("wk_work_task")
@ApiModel(value = "WorkTask object", description = "task table")
public class WorkTask implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "task table")
	@TableId(value = "task_id", type = IdType.AUTO)
	private Integer taskId;

	@ApiModelProperty(value = "task name")
	private String name;

	@ApiModelProperty(value = "Creator ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "responsible person ID")
	private Long mainUserId;

	@ApiModelProperty(value = "Team Member ID")
	private String ownerUserId;

	@ApiModelProperty(value = "New time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "update time")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "Completion Status 1 In Progress 2 Deferred 3 Archive 5 Finished")
	private Integer status;

	@ApiModelProperty(value = "class id")
	private Integer classId;

	@ApiModelProperty(value = "label, number splicing")
	private String labelId;

	@ApiModelProperty(value = "description")
	private String description;

	@ApiModelProperty(value = "Superior ID")
	private Integer pid;

	@ApiModelProperty(value = "start time")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty(value = "end time")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date stopTime;

	@ApiModelProperty(value = "Priority from big to small 3 high 2 medium 1 low 0 none")
	private Integer priority;

	@ApiModelProperty(value = "Project ID")
	private Integer workId;

	@ApiModelProperty(value = "Workbench display 0 inbox 1 to do today, 2 to do next, 3 to do later")
	private Integer isTop;

	@ApiModelProperty(value = "Public or not")
	private Integer isOpen;

	@ApiModelProperty(value = "sort ID")
	private Integer orderNum;

	@ApiModelProperty(value = "My task sort ID")
	private Integer topOrderNum;

	@ApiModelProperty(value = "Archive Time")
	private Date archiveTime;

	@ApiModelProperty(value = "Whether to delete 0 not delete 1 delete")
	private Integer ishidden;

	@ApiModelProperty(value = "Delete time")
	private Date hiddenTime;

	@ApiModelProperty(value = "Batch")
	private String batchId;

	@ApiModelProperty(value = "1 archive")
	private Integer isArchive;

}
