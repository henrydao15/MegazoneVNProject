package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryInsurancePageListBO extends PageEntity {

	@ApiModelProperty
	private Integer iRecordId;

	@ApiModelProperty
	private String employeeName;

	@ApiModelProperty
	private Integer schemeId;

	@ApiModelProperty
	private String city;

	@ApiModelProperty
	private Integer status;
}
