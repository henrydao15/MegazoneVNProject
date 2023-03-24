package com.megazone.hrm.entity.BO;

import com.megazone.hrm.entity.PO.HrmSalarySlipTemplateOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SendSalarySlipBO {

	private List<Integer> sEmpRecordIds;

	@ApiModelProperty
	private Boolean isAll;

	@ApiModelProperty(value = " 0  1 ")
	private Integer hideEmpty;

	@ApiModelProperty(value = "")
	private List<HrmSalarySlipTemplateOption> slipTemplateOption;

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty(value = " 0  1 ")
	private Integer sendStatus;


}
