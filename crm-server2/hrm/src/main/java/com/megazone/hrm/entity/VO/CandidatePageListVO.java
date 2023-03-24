package com.megazone.hrm.entity.VO;

import com.megazone.hrm.entity.PO.HrmRecruitCandidate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CandidatePageListVO extends HrmRecruitCandidate {

	@ApiModelProperty
	private String createUserName;

	@ApiModelProperty
	private String interviewEmployeeName;

	@ApiModelProperty
	private String otherInterviewEmployeeName;
	@ApiModelProperty(value = "id")
	private Integer interviewEmployeeId;

	@ApiModelProperty(value = "")
	private String otherInterviewEmployeeIds;
	@ApiModelProperty
	private Integer interviewType;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private Date interviewTime;

	@ApiModelProperty(value = "")
	private String ownerEmployeeName;

	@ApiModelProperty
	private Integer postStatus;

	private Integer deptId;

	private String deptName;

	@ApiModelProperty(value = " 1 2 3 4 ")
	private Integer interviewResult;
}
