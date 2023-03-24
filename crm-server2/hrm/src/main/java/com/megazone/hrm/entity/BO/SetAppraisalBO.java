package com.megazone.hrm.entity.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.hrm.entity.PO.HrmAchievementAppraisalEvaluators;
import com.megazone.hrm.entity.PO.HrmAchievementAppraisalScoreLevel;
import com.megazone.hrm.entity.PO.HrmAchievementAppraisalTargetConfirmors;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SetAppraisalBO {
	@ApiModelProperty(value = "Id")
	private Integer appraisalId;

	@ApiModelProperty(value = "")
	@NotBlank(message = "")
	private String appraisalName;

	@ApiModelProperty(value = "1  2  3  4 ")
	private Integer cycleType;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	@ApiModelProperty(value = "id")
	private Integer tableId;

	@ApiModelProperty(value = " 1 ")
	private Integer writtenBy;

	@ApiModelProperty(value = "")
	private Collection<Integer> resultConfirmors;

	@ApiModelProperty(value = "")
	private BigDecimal fullScore;

	@ApiModelProperty(value = " 1  0 ")
	private Integer isForce;

	@ApiModelProperty(value = "")
	private Collection<Integer> employeeIds;

	@ApiModelProperty(value = "")
	private Collection<Integer> deptIds;

	@ApiModelProperty(value = "")
	private List<HrmAchievementAppraisalTargetConfirmors> targetConfirmorsList;

	@ApiModelProperty(value = "")
	private List<HrmAchievementAppraisalEvaluators> evaluatorsList;

	@ApiModelProperty(value = "")
	private List<HrmAchievementAppraisalScoreLevel> scoreLevelList;


}
