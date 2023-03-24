package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuerySendDetailListVO {

	private Integer id;

	@ApiModelProperty
	private String employeeName;

	@ApiModelProperty
	private String jobNumber;

	@ApiModelProperty
	private String deptName;

	@ApiModelProperty
	private String post;

	@ApiModelProperty
	private String mobile;

	@ApiModelProperty
	private Integer readStatus;

	@ApiModelProperty
	private String remarks;
}
