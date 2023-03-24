package com.megazone.hrm.entity.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class QueryCandidatePageListBO extends PageEntity {

	@ApiModelProperty
	private String search;

	@ApiModelProperty(value = "")
	private Integer postId;

	@ApiModelProperty(value = "")
	private Integer ownerEmployeeId;

	@ApiModelProperty(value = " 1  2 ")
	private Integer sex;

	@ApiModelProperty(value = "")
	private Integer minAge;

	@ApiModelProperty(value = "")
	private Integer maxAge;

	@ApiModelProperty(value = "")
	private Integer minWorkTime;

	@ApiModelProperty(value = "")
	private Integer maxWorkTime;

	@ApiModelProperty(value = " 1 2 3 4 5 6 7")
	private Integer education;

	@ApiModelProperty(value = "")
	private String graduateSchool;

	@ApiModelProperty(value = "")
	private String latestWorkPlace;

	@ApiModelProperty(value = "")
	private Integer channelId;

	@ApiModelProperty(value = "id")
	private Integer interviewEmployeeId;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private List<Date> interviewTime;

	@ApiModelProperty(value = "")
	private Integer status;

	@ApiModelProperty
	private Long createUserId;

	@ApiModelProperty
	@JsonFormat(pattern = "yyyy-MM-dd")
	private List<Date> createTime;

	@ApiModelProperty
	private Integer recruitSurvey;
}
