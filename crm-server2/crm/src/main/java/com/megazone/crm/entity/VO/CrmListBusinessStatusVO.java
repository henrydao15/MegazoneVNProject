package com.megazone.crm.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author
 */
@Data
@ToString
@ApiModel
public class CrmListBusinessStatusVO {

	@ApiModelProperty
	public Integer isEnd;

	@ApiModelProperty
	public String statusName;

	@ApiModelProperty
	public Integer currentProgress;

	@ApiModelProperty
	public Integer totalProgress;

}
