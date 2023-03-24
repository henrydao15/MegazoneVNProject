package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel
public class CrmAuditExamineBO {

	@ApiModelProperty
	private Integer recordId;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private Integer id;

	@ApiModelProperty
	private String remarks;

	@ApiModelProperty
	private Long nextUserId;

	@ApiModelProperty
	private Long ownerUserId;
}
