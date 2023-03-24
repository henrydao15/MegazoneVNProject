package com.megazone.examine.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ExamineConditionDataSaveBO {

	@ApiModelProperty(value = "ID")
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1  2  3  4 5  6  7   8  9  10  11  12  13  14  15 16  17  18  19  20  21 ")
	private Integer type;

	@ApiModelProperty(value = "")
	private String fieldName;

	@ApiModelProperty(value = " 1  2  3  4  5  6  7  8 // 11")
	private Integer conditionType;

	@ApiModelProperty(value = "，json")
	private Object values;
}
