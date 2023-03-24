package com.megazone.crm.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CrmInfoNumVO {

	@ApiModelProperty
	private Integer businessCount;

	@ApiModelProperty
	private Integer callRecordCount;

	@ApiModelProperty
	private Integer contactCount;

	@ApiModelProperty
	private Integer contractCount;

	@ApiModelProperty
	private Integer fileCount;

	@ApiModelProperty
	private Integer invoiceCount;

	@ApiModelProperty
	private Integer memberCount;

	@ApiModelProperty
	private Integer receivablesCount;

	@ApiModelProperty
	private Integer returnVisitCount;

	@ApiModelProperty
	private Integer productCount;
}
