package com.megazone.hrm.entity.VO;

import com.megazone.hrm.entity.PO.HrmRecruitPost;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitPostVO extends HrmRecruitPost {


	@ApiModelProperty
	private Integer hasEntryNum;

	@ApiModelProperty
	private String recruitSchedule;

	@ApiModelProperty
	private String postTypeName;

	@ApiModelProperty(value = "")
	private String ownerEmployeeName;

	@ApiModelProperty(value = "")
	private String interviewEmployeeName;
}
