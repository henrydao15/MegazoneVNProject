package com.megazone.hrm.entity.VO;

import com.megazone.hrm.entity.PO.HrmSalarySlipOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuerySalarySlipListVO {

	private Integer id;

	private Integer year;

	private Integer month;

	private Integer readStatus;

	@ApiModelProperty
	private List<HrmSalarySlipOption> salarySlipOptionList;
}
