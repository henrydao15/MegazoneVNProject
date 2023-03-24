package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRecruitChannelBO {

	@ApiModelProperty
	private Integer deleteChannelId;

	@ApiModelProperty
	private Integer changeChannelId;
}
