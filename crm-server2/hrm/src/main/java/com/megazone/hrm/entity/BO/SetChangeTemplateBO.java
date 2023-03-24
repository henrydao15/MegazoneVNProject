package com.megazone.hrm.entity.BO;

import com.megazone.hrm.entity.VO.ChangeSalaryOptionVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetChangeTemplateBO {

	private Integer id;

	@ApiModelProperty(value = "")
	private String templateName;

	@ApiModelProperty
	private List<ChangeSalaryOptionVO> value;

}
