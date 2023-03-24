package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuerySendDetailListBO extends PageEntity {

	private Integer id;

	private String search;

	@ApiModelProperty
	private Integer readStatus;

	@ApiModelProperty
	private String remarks;

	@ApiModelProperty
	private Integer deptId;
}
