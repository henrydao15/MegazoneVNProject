package com.megazone.core.feign.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class CallUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty
	private List<Long> userIds;

	@ApiModelProperty
	private Integer state;

	@ApiModelProperty
	private Integer hisUse;
}
