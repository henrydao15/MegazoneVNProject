package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateInsuranceSchemeBO {
	@ApiModelProperty
	private List<Integer> employeeIds;

	@ApiModelProperty(value = "id")
	private Integer schemeId;
}
