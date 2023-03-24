package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuerySalaryPageListBO extends PageEntity {

	@ApiModelProperty
	private Integer sRecordId;

	private Integer employeeId;

	private Integer deptId;

	private Integer type;

	private String employeeName;

}
