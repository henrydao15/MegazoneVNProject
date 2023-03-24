package com.megazone.hrm.entity.BO;

import com.megazone.hrm.entity.PO.HrmSalarySlipTemplateOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class AddSlipTemplateBO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	@NotEmpty(message = "")
	private String templateName;

	@ApiModelProperty(value = " 0  1 ")
	private Integer hideEmpty;

	@ApiModelProperty(value = "")
	private List<HrmSalarySlipTemplateOption> slipTemplateOption;

}
