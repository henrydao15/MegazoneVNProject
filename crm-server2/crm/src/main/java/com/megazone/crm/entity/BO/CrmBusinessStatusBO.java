package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Administrator
 */
@Data
@ToString
@ApiModel
public class CrmBusinessStatusBO {

	@ApiModelProperty
	private Integer businessId;

	@ApiModelProperty
	private Integer statusId;

	@ApiModelProperty
	private Integer isEnd;

	private String statusRemark;
}
