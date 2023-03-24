package com.megazone.hrm.entity.VO;

import com.megazone.hrm.entity.PO.HrmEmployeeSalaryCard;
import com.megazone.hrm.entity.PO.HrmEmployeeSocialSecurityInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalarySocialSecurityVO {

	@ApiModelProperty
	private HrmEmployeeSalaryCard salaryCard;

	@ApiModelProperty
	private HrmEmployeeSocialSecurityInfo socialSecurityInfo;
}
