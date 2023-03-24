package com.megazone.core.feign.examine.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author
 * @date 2020/12/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamineConditionDataBO {
	@ApiModelProperty
	private Integer label;

	@ApiModelProperty
	private Integer typeId;

	@ApiModelProperty
	private Integer categoryId;

	@ApiModelProperty
	private List<String> fieldList;

	@ApiModelProperty
	private Integer checkStatus;
}
