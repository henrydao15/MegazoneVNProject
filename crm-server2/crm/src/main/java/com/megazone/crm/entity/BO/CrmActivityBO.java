package com.megazone.crm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class CrmActivityBO extends PageEntity {

	@ApiModelProperty
	private Integer crmType;

	@ApiModelProperty
	private Integer activityType;

	@ApiModelProperty
	private Integer activityTypeId;

	private String search;

	private Integer intervalDay;

	private String startDate;

	private String endDate;
}
