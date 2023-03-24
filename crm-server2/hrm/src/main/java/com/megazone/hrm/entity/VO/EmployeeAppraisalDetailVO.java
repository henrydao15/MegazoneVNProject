package com.megazone.hrm.entity.VO;

import com.megazone.hrm.entity.PO.HrmAchievementEmployeeSeg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class EmployeeAppraisalDetailVO {

	@ApiModelProperty
	private Integer employeeId;

	@ApiModelProperty
	private String employeeName;

	@ApiModelProperty
	private String mobile;

	@ApiModelProperty(value = "")
	private BigDecimal score;

	@ApiModelProperty(value = "id")
	private Integer levelId;

	@ApiModelProperty(value = "")
	private String levelName;

	@ApiModelProperty
	private BigDecimal fullScore;

	@ApiModelProperty(value = " 1  2  3  4  5 ")
	private Integer status;

	@ApiModelProperty(value = " 0  1  2  3  4 ")
	private Integer appraisalStatus;

	@ApiModelProperty
	private Integer isWrite;

	@ApiModelProperty
	private List<SimpleHrmEmployeeVO> confirmorsList;

	@ApiModelProperty
	private List<EvaluatorsVO> evaluatorsList;

	@ApiModelProperty
	private List<SimpleHrmEmployeeVO> resultConfirmors;

	@ApiModelProperty
	private List<HrmAchievementEmployeeSeg> fixedSegList;

	@ApiModelProperty
	private List<HrmAchievementEmployeeSeg> noFixedSegList;

	@ApiModelProperty
	private List<EvaluatoResultVO> evaluatoResultList;

	@ApiModelProperty
	private AchievementTableTempVO tableTemp;


	@Getter
	@Setter
	public static class EvaluatorsVO extends SimpleHrmEmployeeVO {

		@ApiModelProperty(value = "")
		private BigDecimal weight;
	}


}
