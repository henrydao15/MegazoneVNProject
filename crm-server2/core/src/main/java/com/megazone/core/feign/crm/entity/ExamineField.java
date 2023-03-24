package com.megazone.core.feign.crm.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel
@Data
public class ExamineField implements Serializable {

	@ApiModelProperty
	private Integer fieldId;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private String fieldName;

	@ApiModelProperty
	private Integer type;

	@ApiModelProperty
	private Integer fieldType;

	@ApiModelProperty(value = "")
	private List<String> setting;

	public ExamineField(Integer fieldId, String name, String fieldName, Integer type, Integer fieldType) {
		this.fieldId = fieldId;
		this.name = name;
		this.fieldName = fieldName;
		this.type = type;
		this.fieldType = fieldType;
	}

	public ExamineField() {
	}
}
