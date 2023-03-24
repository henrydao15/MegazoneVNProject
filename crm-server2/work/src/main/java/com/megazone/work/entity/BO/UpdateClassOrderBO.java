package com.megazone.work.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel
public class UpdateClassOrderBO {
	@ApiModelProperty
	private Integer workId;

	@ApiModelProperty
	private List<Integer> classIds;
}
