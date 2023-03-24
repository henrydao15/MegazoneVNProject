package com.megazone.crm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@ApiModel
public class CrmContactsPageBO extends PageEntity {

	@ApiModelProperty
	private Integer customerId;

	@ApiModelProperty
	private Integer businessId;

	@ApiModelProperty
	private String search;

	@ApiModelProperty
	private Integer checkStatus;
}
