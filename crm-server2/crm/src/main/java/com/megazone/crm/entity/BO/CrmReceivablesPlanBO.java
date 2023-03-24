package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Data
@ApiModel
public class CrmReceivablesPlanBO {

	@ApiModelProperty
	private Integer contractId;

	@ApiModelProperty
	private Integer customerId;

	@ApiModelProperty
	private Integer receivablesId;

	@ApiModelProperty(value = "")
	private BigDecimal money;

	@ApiModelProperty(value = "")
	private String returnDate;

	@ApiModelProperty(value = "")
	private String returnType;

	@ApiModelProperty(value = "")
	private Integer remind;

	@ApiModelProperty(value = "")
	private String remindDate;

	@ApiModelProperty(value = "")
	private String remark;
}
