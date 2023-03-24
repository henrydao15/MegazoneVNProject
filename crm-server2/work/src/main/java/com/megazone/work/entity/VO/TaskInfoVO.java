package com.megazone.work.entity.VO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.work.entity.BO.TaskLabelBO;
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

	@ApiModelProperty(value = "id")
	@TableId(value = "task_id", type = IdType.AUTO)
	private Integer taskId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String workName;
	@ApiModelProperty(value = "")
	private Integer fileNum;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "ID")
	private Long mainUserId;

	@ApiModelProperty(value = "")
	private String mainUserName;

	@ApiModelProperty(value = "")
	private String mainUserImg;

	@ApiModelProperty(value = "ID")
	private String ownerUserId;

	@ApiModelProperty(value = "")
	private String ownerUserName;

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
	private Integer classOrder;

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


	@ApiModelProperty(value = " 0 1")
	private Integer isEnd;

	@ApiModelProperty(value = "")
	private SimpleUser mainUser;

	@ApiModelProperty(value = "")
	private List<TaskLabelBO> labelList;

	@ApiModelProperty(value = "id")
	private String customerIds;

	@ApiModelProperty(value = "id")
	private String contactsIds;

	@ApiModelProperty(value = "id")
	private String businessIds;

	@ApiModelProperty(value = "id")
	private String contractIds;

	@ApiModelProperty(value = "")
	private Integer relationCount;

	@ApiModelProperty(value = "")
	private Integer commentCount;

	@ApiModelProperty(value = "")
	private Integer childWCCount;

	@ApiModelProperty(value = "")
	private Integer childAllCount;

	@ApiModelProperty(value = "")
	private List<SimpleUser> ownerUserList;
}
