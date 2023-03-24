package com.megazone.core.feign.examine.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class ExamineRecordSaveBO {

	@ApiModelProperty
	private Integer label;

	@ApiModelProperty
	private Integer typeId;

	@ApiModelProperty
	private Integer recordId;


	@ApiModelProperty
	private Integer categoryId;

	@ApiModelProperty
	private Map<String, Object> dataMap;

	@ApiModelProperty
	private List<ExamineGeneralBO> optionalList;

	@ApiModelProperty
	private String title;

	@ApiModelProperty
	private String content;

}
