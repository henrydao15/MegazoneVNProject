package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoRemindVO {

	@ApiModelProperty
	private Integer toSalaryExamine;
	@ApiModelProperty
	private Integer toLeave;
	@ApiModelProperty
	private Integer toExpireContract;
	@ApiModelProperty
	private Integer toCorrect;
	@ApiModelProperty
	private Integer toIn;
	@ApiModelProperty
	private Integer toBirthday;

}
