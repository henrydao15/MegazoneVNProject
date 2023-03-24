package com.megazone.core.feign.crm.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CrmSaveExamineRecordBO {

	@ApiModelProperty
	private Integer type;

	@ApiModelProperty
	private Long userId;

	@ApiModelProperty
	private Long ownerUserId;

	@ApiModelProperty
	private Integer recordId;

	@ApiModelProperty
	private Integer status;
}
