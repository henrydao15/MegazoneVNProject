package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2021/03/19/10:56
 */
@Getter
@Setter
public class QueryLevelIdByScoreBO {
	@ApiModelProperty
	@NotNull
	private Integer employeeAppraisalId;
	@ApiModelProperty(value = "")
	private BigDecimal score;
}
