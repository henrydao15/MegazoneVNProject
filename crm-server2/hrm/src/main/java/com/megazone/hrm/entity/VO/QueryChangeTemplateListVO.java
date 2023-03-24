package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QueryChangeTemplateListVO {
	private Integer id;

	@ApiModelProperty(value = "")
	private String templateName;

	@ApiModelProperty(value = " 1  2 ")
	private Integer recordType;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isDefault;

	@ApiModelProperty
	private List<ChangeSalaryOptionVO> value;
}
