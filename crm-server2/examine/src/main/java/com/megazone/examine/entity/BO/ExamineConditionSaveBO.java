package com.megazone.examine.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ExamineConditionSaveBO {

	@ApiModelProperty
	private Integer sort;

	@ApiModelProperty
	private String conditionName;

	@ApiModelProperty
	private List<ExamineDataSaveBO> examineDataList;

	@ApiModelProperty
	private List<ExamineConditionDataSaveBO> conditionDataList;
}
