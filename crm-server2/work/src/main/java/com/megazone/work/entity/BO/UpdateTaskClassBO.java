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
public class UpdateTaskClassBO {
	@ApiModelProperty(value = "id")
	private Integer fromId;

	@ApiModelProperty(value = "id")
	private List<Integer> fromList;

	@ApiModelProperty(value = "id")
	private Integer toId;

	@ApiModelProperty(value = "id")
	private List<Integer> toList;
}
