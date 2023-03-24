package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ResultConfirmByAppraisalIdVO {

	@ApiModelProperty
	private Integer appraisalId;

	@ApiModelProperty
	private String appraisalName;

	@ApiModelProperty
	private Integer totalNum;

	@ApiModelProperty
	private Integer waitingNum;

	@ApiModelProperty
	private BigDecimal fullScore;

	@ApiModelProperty(value = " 1  0 ")
	private Integer isForce;

	@ApiModelProperty
	private Integer hasInRange;

	@ApiModelProperty
	private List<LevelDetailInfosBean> levelDetailInfos;

	@Getter
	@Setter
	public static class LevelDetailInfosBean {

		@ApiModelProperty
		private Integer levelId;

		@ApiModelProperty
		private String levelName;

		@ApiModelProperty(value = "")
		private BigDecimal minScore;

		@ApiModelProperty(value = "")
		private BigDecimal maxScore;

		@ApiModelProperty(value = "")
		private Integer minNum;

		@ApiModelProperty(value = "")
		private Integer maxNum;

		@ApiModelProperty
		private BigDecimal actualWeight;

		@ApiModelProperty
		private Integer actualNum;

		@ApiModelProperty
		private List<EmployeesBean> employees;


	}

	@Getter
	@Setter
	public static class EmployeesBean {

		@ApiModelProperty(value = "id")
		private Integer employeeId;

		@ApiModelProperty(value = "")
		private String employeeName;

		@ApiModelProperty(value = "")
		private String mobile;

		@ApiModelProperty(value = "id")
		private Integer employeeAppraisalId;

		@ApiModelProperty(value = "")
		private BigDecimal score;

		@ApiModelProperty(value = "")
		private Integer levelId;
		@ApiModelProperty(value = "")
		private String deptName;

		@ApiModelProperty(value = "")
		private String post;


	}
}
