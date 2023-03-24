package com.megazone.hrm.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QueryCleanCandidateBO {

	@ApiModelProperty
	private List<Integer> status;

	@ApiModelProperty
	private Integer day;

}
