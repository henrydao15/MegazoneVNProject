package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class CrmSceneConfigBO {

	@ApiModelProperty
	private List<Integer> noHideIds;

	@ApiModelProperty
	private List<Integer> hideIds;

	@ApiModelProperty
	private Integer type;
}
