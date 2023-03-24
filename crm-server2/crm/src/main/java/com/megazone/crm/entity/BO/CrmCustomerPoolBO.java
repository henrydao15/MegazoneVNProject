package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class CrmCustomerPoolBO {

	@ApiModelProperty
	private List<Integer> ids;

	@ApiModelProperty
	private Integer poolId;

	@ApiModelProperty
	private Long userId;
}
