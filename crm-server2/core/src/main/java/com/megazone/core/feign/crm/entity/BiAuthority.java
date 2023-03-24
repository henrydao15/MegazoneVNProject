package com.megazone.core.feign.crm.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author
 */
@ApiModel
@Data
public class BiAuthority {

	@ApiModelProperty
	private List<Long> userIds;

	@ApiModelProperty
	private List<Integer> deptIds;
}
