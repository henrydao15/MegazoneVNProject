package com.megazone.examine.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
@ApiModel
public class ExamineFlowConditionDataVO {

	@ApiModelProperty(value = "ID")
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String fieldName;

	@ApiModelProperty(value = " 1  2  3  4 5  6  7   8  9  10  11  12  13  14  15 16  17  18  19  20  21 ")
	private Integer type;

	@ApiModelProperty(value = " 1  2  3  4  5  6  7  8 // 11")
	private Integer conditionType;

	@ApiModelProperty(value = "，json")
	private Object values;


	@ApiModelProperty(value = "json")
	private String backupValue;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ExamineFlowConditionDataVO that = (ExamineFlowConditionDataVO) o;
		return Objects.equals(fieldName, that.fieldName) &&
				Objects.equals(conditionType, that.conditionType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fieldName, conditionType);
	}
}
