package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryRecruitPostPageListBO extends PageEntity {

	@ApiModelProperty
	private String postName;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private String city;

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty
	private Integer ownerEmployeeId;

	@ApiModelProperty(value = "  1  2 3 4 5 6 7")
	private Integer jobNature;


}
