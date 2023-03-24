package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@ApiModel
public class CrmFieldVerifyBO {

	@ApiModelProperty
	private Integer fieldId;

	@ApiModelProperty
	private String value;

	@ApiModelProperty
	private String batchId;

	@ApiModelProperty
	private Integer status = 0;

	@ApiModelProperty
	private String ownerUserName;

	@ApiModelProperty
	private List<String> poolNames;
}
