package com.megazone.bi.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class ProductStatisticsVO {

	@ApiModelProperty
	private Integer productId;

	@ApiModelProperty
	private String productName;

	@ApiModelProperty
	private String categoryName;

	@ApiModelProperty
	private Integer num;

	@ApiModelProperty
	private Integer contractNum;

	@ApiModelProperty
	private BigDecimal total;

	@ApiModelProperty
	private Integer count;
}
