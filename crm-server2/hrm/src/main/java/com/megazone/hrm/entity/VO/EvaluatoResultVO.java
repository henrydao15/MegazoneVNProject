package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class EvaluatoResultVO extends SimpleHrmEmployeeVO {
	@ApiModelProperty(value = "")
	private BigDecimal weight;

	@ApiModelProperty(value = "")
	private BigDecimal score;

	@ApiModelProperty(value = "id")
	private Integer levelId;

	@ApiModelProperty(value = "")
	private String levelName;

	@ApiModelProperty(value = "")
	private String evaluate;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private Date createTime;
}
