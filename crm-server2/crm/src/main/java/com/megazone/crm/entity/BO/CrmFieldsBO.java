package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author
 */
@Data
@ToString
@ApiModel(value = "CrmFields", description = "")
public class CrmFieldsBO {

	@ApiModelProperty(value = " 1  2  3  4  5  6  78. 18 ")
	private Integer label;

	@ApiModelProperty(value = "")
	private String updateTime;

	@ApiModelProperty(value = "")
	private String name;
}
