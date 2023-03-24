package com.megazone.examine.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ExamineFlowConditionVO {

	@ApiModelProperty
	private Integer sort;

	@ApiModelProperty
	private String conditionName;

	@ApiModelProperty
	private List<ExamineFlowVO> examineDataList;

	@ApiModelProperty
	private List<ExamineFlowConditionDataVO> conditionDataList;
}
