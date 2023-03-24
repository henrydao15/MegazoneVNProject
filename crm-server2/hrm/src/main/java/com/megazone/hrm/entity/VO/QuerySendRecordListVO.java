package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class QuerySendRecordListVO {

	private Integer id;

	@ApiModelProperty(value = "")
	private String createUserName;

	@ApiModelProperty(value = "id")
	private Long createUserId;

	@ApiModelProperty(value = "")
	private Date createTime;

	@ApiModelProperty(value = "")
	private Integer salaryNum;

	@ApiModelProperty(value = "")
	private Integer payNum;

	@ApiModelProperty(value = "")
	private Integer readNum;

	private Integer year;

	private Integer month;
}
