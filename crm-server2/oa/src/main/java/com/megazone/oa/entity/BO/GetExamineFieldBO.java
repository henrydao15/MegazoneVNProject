package com.megazone.oa.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetExamineFieldBO {

	Integer examineId;
	@ApiModelProperty
	Integer isDetail;

	String type;
}
