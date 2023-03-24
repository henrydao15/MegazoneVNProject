package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EvaluatoListVO extends TargetConfirmListVO {

	@ApiModelProperty
	private Integer status;
}
