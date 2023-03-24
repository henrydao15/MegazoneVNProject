package com.megazone.work.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.work.entity.VO.TaskInfoVO;
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
@TableName("wk_work_task")
@ApiModel(value = "WorkTask", description = "")
public class WorkTask implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	@TableId(value = "task_id", type = IdType.AUTO)
	private Integer taskId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "ID")
	private Long mainUserId;

	@ApiModelProperty(value = "ID")
	private String ownerUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = " 123 5")
	private Integer status;

	@ApiModelProperty(value = "id")
	private Integer classId;

	@ApiModelProperty(value = " ,")
	private String labelId;

	@ApiModelProperty(value = "")
	private String description;

	@ApiModelProperty(value = "ID")
	private Integer pid;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date stopTime;

	@ApiModelProperty(value = "  3 2 1 0")
	private Integer priority;

	@ApiModelProperty(value = "ID")
	private Integer workId;

	@ApiModelProperty(value = " 0 1，2，3")
	private Integer isTop;

	@ApiModelProperty(value = "")
	private Integer isOpen;

	@ApiModelProperty(value = "ID")
	private Integer orderNum;

	@ApiModelProperty(value = "ID")
	private Integer topOrderNum;

	@ApiModelProperty(value = "")
	private Date archiveTime;

	@ApiModelProperty(value = " 0  1 ")
	private Integer ishidden;

	@ApiModelProperty(value = "")
	private Date hiddenTime;

	@ApiModelProperty(value = "")
	private String batchId;

	@ApiModelProperty(value = "1")
	private Integer isArchive;


	@TableField(exist = false)
	private String customerIds;
	@TableField(exist = false)
	private String contactsIds;
	@TableField(exist = false)
	private String businessIds;
	@TableField(exist = false)
	private String contractIds;

	@TableField(exist = false)
	private List<TaskInfoVO> taskInfoList;


}
