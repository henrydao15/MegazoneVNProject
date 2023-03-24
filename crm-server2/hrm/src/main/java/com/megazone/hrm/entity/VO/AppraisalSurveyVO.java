package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AppraisalSurveyVO {


	private Integer appraisalId;

	@ApiModelProperty(value = "")
	private String appraisalName;

	@ApiModelProperty(value = "id")
	private Integer tableId;

	private String appraisalTime;

	@ApiModelProperty
	private String tableName;

	@ApiModelProperty(value = "")
	private Date startTime;

	@ApiModelProperty(value = "")
	private Date endTime;

	@ApiModelProperty
	private List<ScoreLevelsBean> scoreLevels;

	@Getter
	@Setter
	public static class ScoreLevelsBean {

		@ApiModelProperty
		private int levelId;
		@ApiModelProperty
		private String levelName;
		@ApiModelProperty
		private int num;
	}
}
