package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ResultConfirmListVO {

	@ApiModelProperty
	private Integer appraisalId;

	@ApiModelProperty
	private String appraisalName;

	@ApiModelProperty
	private Date startTime;

	@ApiModelProperty
	private Date endTime;

	@ApiModelProperty
	private String appraisalTime;

	@ApiModelProperty
	private Integer totalNum;

	@ApiModelProperty
	private Integer waitingNum;

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
