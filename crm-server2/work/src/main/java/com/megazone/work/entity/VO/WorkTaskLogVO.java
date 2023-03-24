package com.megazone.work.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wyq
 */
@Data
@ApiModel
public class WorkTaskLogVO {

	@ApiModelProperty(value = "id")
	private Integer logId;

	@ApiModelProperty(value = "")
	private String content;

	@ApiModelProperty(value = "")
	private Date createTime;

	@ApiModelProperty(value = "")
	private String img;

	@ApiModelProperty(value = "")
	private String realname;
}
