package com.megazone.admin.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class AdminMessageVO {

	@ApiModelProperty
	private Integer allCount;

	@ApiModelProperty
	private Integer announceCount;

	@ApiModelProperty
	private Integer examineCount;

	@ApiModelProperty
	private Integer taskCount;

	@ApiModelProperty
	private Integer logCount;

	@ApiModelProperty
	private Integer crmCount;

	@ApiModelProperty
	private Integer eventCount;

	@ApiModelProperty
	private Integer knowledgeCount;
	@ApiModelProperty
	private Integer hrmCount;
	@ApiModelProperty
	private Integer jxcCount;
}
