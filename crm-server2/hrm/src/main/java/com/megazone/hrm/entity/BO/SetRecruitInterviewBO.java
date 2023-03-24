package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SetRecruitInterviewBO {

	@ApiModelProperty(value = "id")
	private Integer interviewId;

	@ApiModelProperty(value = "id()")
	private Integer candidateId;

	@ApiModelProperty(value = "id()")
	private List<Integer> candidateIds;

	@ApiModelProperty(value = " 1 2 3")
	private Integer type;

	@ApiModelProperty(value = "id")
	private Integer interviewEmployeeId;

	@ApiModelProperty(value = "")
	private List<Integer> otherInterviewEmployeeIds;

	@ApiModelProperty(value = "")
	private Date interviewTime;

	@ApiModelProperty(value = "")
	private String address;
}
