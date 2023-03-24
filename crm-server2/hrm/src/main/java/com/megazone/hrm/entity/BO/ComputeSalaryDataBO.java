package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComputeSalaryDataBO {

	@ApiModelProperty
	private Boolean isSyncInsuranceData;

	@ApiModelProperty
	private Integer sRecordId;
}
