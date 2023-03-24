package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuerySalarySlipListBO extends PageEntity {

	@ApiModelProperty(name = "", example = "yyyy-mm")
	private String startTime;

	@ApiModelProperty(name = "", example = "yyyy-mm")
	private String endTime;

	@ApiModelProperty
	private Integer orderType;

	@ApiModelProperty
	private Integer order;


}
