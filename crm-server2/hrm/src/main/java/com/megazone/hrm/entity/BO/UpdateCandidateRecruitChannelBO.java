package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateCandidateRecruitChannelBO {

	@ApiModelProperty
	private List<Integer> candidateIds;

	@ApiModelProperty
	private Integer channelId;

}
