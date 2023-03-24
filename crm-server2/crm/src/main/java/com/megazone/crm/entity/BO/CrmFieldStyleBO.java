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
@ApiModel(value = "CrmFieldStyle", description = "")
public class CrmFieldStyleBO {
	@ApiModelProperty(value = "ID")
	private Integer id;
	@ApiModelProperty(value = "")
	private Integer width;
	@ApiModelProperty(value = "label")
	private Integer label;
	@ApiModelProperty(value = "id")
	private Integer poolId;
	private String field;
}
