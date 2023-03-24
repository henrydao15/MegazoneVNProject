package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class EmployeeSelectFiledVO {

	@ApiModelProperty(value = "ID")
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String fieldName;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = " 1  2  3  4 5  6  7   8  9    10  11  12 ")
	private Integer type;

	@ApiModelProperty(value = " 0  1 hrm 2 hrm 3 hrm 4  5  6 ")
	private Integer componentType;

	@ApiModelProperty(value = "，，kv")
	private String options;


}
