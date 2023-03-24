package com.megazone.work.entity.VO;

import com.megazone.core.common.JSONObject;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.work.entity.BO.WorkTaskLabelBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel
public class TaskDetailVO {
	@ApiModelProperty(value = "id")
	private Integer taskId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "ID")
	private Long createUserId;

	@ApiModelProperty(value = "")
	private SimpleUser createUser;

	@ApiModelProperty(value = "ID")
	private Long mainUserId;

	@ApiModelProperty(value = "")
	private SimpleUser mainUser;

	@ApiModelProperty(value = "ID")
	private String ownerUserId;

	@ApiModelProperty(value = "")
	private List<SimpleUser> ownerUserList;

	@ApiModelProperty(value = "")
	private Date createTime;

	@ApiModelProperty(value = "")
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
	private Date startTime;

	@ApiModelProperty(value = "")
	private Object stopTime;

	@ApiModelProperty(value = "  3 2 1 0")
	private Integer priority;

	@ApiModelProperty(value = "ID")
	private Integer workId;

	@ApiModelProperty(value = "")
	private String workName;

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


	@ApiModelProperty(value = "")
	private List<TaskDetailVO> childTask;

	@ApiModelProperty(value = "")
	private List<FileEntity> file;

	@ApiModelProperty(value = "")
	private List<SimpleCrmEntity> customerList = new ArrayList<>();

	@ApiModelProperty(value = "")
	private List<SimpleCrmEntity> contactsList = new ArrayList<>();

	@ApiModelProperty(value = "")
	private List<SimpleCrmEntity> businessList = new ArrayList<>();

	@ApiModelProperty(value = "")
	private List<SimpleCrmEntity> contractList = new ArrayList<>();

	@ApiModelProperty(value = "")
	private List<WorkTaskLabelBO> labelList;

	private JSONObject authList;
}
