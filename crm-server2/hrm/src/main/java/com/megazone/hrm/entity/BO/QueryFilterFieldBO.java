package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel
public class QueryFilterFieldBO {

	@ApiModelProperty
	private Integer type;
	@ApiModelProperty
	private Integer conditionType;
	@ApiModelProperty
	private List<String> value;
	@ApiModelProperty
	private String name;
	@ApiModelProperty
	private String start;
	@ApiModelProperty
	private String end;
}
