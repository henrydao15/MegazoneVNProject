package com.megazone.hrm.entity.BO;

import com.megazone.hrm.entity.PO.HrmSalaryOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetSalaryOptionBO {

	@ApiModelProperty
	private List<HrmSalaryOption> optionList;

	@ApiModelProperty
	private List<Integer> hideList;
}
