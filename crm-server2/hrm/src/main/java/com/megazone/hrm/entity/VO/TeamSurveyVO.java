package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author
 */
@Data
public class TeamSurveyVO {


	@ApiModelProperty
	private List<TeamSurveyPropertiesVO> statusAnalysis;

	@ApiModelProperty
	private List<TeamSurveyPropertiesVO> sexAnalysis;

	@ApiModelProperty
	private List<TeamSurveyPropertiesVO> ageAnalysis;

	@ApiModelProperty
	private List<TeamSurveyPropertiesVO> companyAgeAnalysis;
}
