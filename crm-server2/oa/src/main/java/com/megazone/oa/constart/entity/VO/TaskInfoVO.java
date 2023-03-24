package com.megazone.oa.constart.entity.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.oa.constart.entity.BO.TaskLabelBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel
@Accessors(chain = true)
public class TaskInfoVO {

	@ApiModelProperty(value = "task id")
	@TableId(value = "task_id", type = IdType.AUTO)
	private Integer taskId;

	@ApiModelProperty(value = "task name")
	private String name;

	@ApiModelProperty(value = "Creator ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "responsible person ID")
	private Long mainUserId;

	@ApiModelProperty(value = "Name of person in charge")
	private String mainUserName;

	@ApiModelProperty(value = "Person in charge")
	private String mainUserImg;

	@ApiModelProperty(value = "Team Member ID")
	private String ownerUserId;

	@ApiModelProperty(value = "team member name")
	private String ownerUserName;

	@ApiModelProperty(value = "New time")
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date createTime;

	@ApiModelProperty(value = "update time")
	@TableField(fill = FieldFill.UPDATE)
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
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
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty(value = "end time")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
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

	@ApiModelProperty(value = "sort ID")
	private Integer classOrder;

	@ApiModelProperty(value = "My task sort ID")
	private Integer topOrderNum;

	@ApiModelProperty(value = "Archive Time")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date archiveTime;

	@ApiModelProperty(value = "Whether to delete 0 not delete 1 delete")
	private Integer ishidden;

	@ApiModelProperty(value = "Delete time")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date hiddenTime;

	@ApiModelProperty(value = "batch")
	private String batchId;

	@ApiModelProperty(value = "1 archive")
	private Integer isArchive;


	@ApiModelProperty(value = "Is the task due 0 not due 1 due")
	private Integer isEnd;

	@ApiModelProperty(value = "responsible person information")
	private SimpleUser mainUser;

	@ApiModelProperty(value = "List of tags")
	private List<TaskLabelBO> labelList;

	@ApiModelProperty(value = "Associated Customer ID")
	private String customerIds;

	@ApiModelProperty(value = "Associated Contact ID")
	private String contactsIds;

	@ApiModelProperty(value = "Associated opportunity id")
	private String businessIds;

	@ApiModelProperty(value = "Associated contract id")
	private String contractIds;

	@ApiModelProperty(value = "Number of associated businesses")
	private Integer relationCount;

	@ApiModelProperty(value = "Number of comments")
	private Integer commentCount;

	@ApiModelProperty(value = "Number of subtasks completed")
	private Integer childWCCount;

	@ApiModelProperty(value = "Number of subtasks")
	private Integer childAllCount;

	@ApiModelProperty(value = "team member")
	private List<SimpleUser> ownerUserList;
}
