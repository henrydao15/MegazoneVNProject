package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UpdateSalaryBO {

	@ApiModelProperty
	private Integer sEmpRecordId;

	@ApiModelProperty
	private Map<Integer, String> salaryValues;

}
