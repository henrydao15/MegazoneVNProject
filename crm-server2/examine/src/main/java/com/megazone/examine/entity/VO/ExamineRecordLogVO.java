package com.megazone.examine.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author
 * @date 2020/12/19
 */
@Data
public class ExamineRecordLogVO {

	private Integer logId;

	@ApiModelProperty(value = "ID")
	private Long examineId;

	@ApiModelProperty(value = "ID")
	private Integer flowId;

	@ApiModelProperty(value = "ID")
	private Integer recordId;

	@ApiModelProperty(value = "1  2  3 ")
	private Integer type;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "")
	private Integer sort;

	@ApiModelProperty(value = "0、1、2、3 4: 5  6  7  8 ")
	private Integer examineStatus;

	@ApiModelProperty(value = "ID")
	private Long examineUserId;

	@ApiModelProperty(value = "")
	private String examineUserName;

	@ApiModelProperty(value = "ID")
	private Integer examineRoleId;

	@ApiModelProperty(value = "ID")
	private Long createUserId;

	private Date createTime;

	@ApiModelProperty(value = "")
	private Date examineTime;


}
