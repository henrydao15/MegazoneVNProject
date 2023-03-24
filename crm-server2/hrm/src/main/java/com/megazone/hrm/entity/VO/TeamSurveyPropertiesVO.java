package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author
 */
@Data
@Builder
public class TeamSurveyPropertiesVO {


	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private String proportion;

	@ApiModelProperty
	private Long count;
}
