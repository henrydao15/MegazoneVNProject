package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecruitSurveyCountVO {

	@ApiModelProperty
	Integer postCount;

	@ApiModelProperty
	Integer chooseCount;

	@ApiModelProperty
	Integer pendingEntryCount;

	@ApiModelProperty
	Integer haveJoinedCount;
}
