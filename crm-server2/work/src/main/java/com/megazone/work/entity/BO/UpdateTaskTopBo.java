package com.megazone.work.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel
public class UpdateTaskTopBo {
	@ApiModelProperty(value = "id")
	@NotNull
	private Integer fromTopId;

	@ApiModelProperty(value = "id")
	private List<Integer> fromList;

	@ApiModelProperty(value = "id")
	@NotNull
	private Integer toTopId;

	@ApiModelProperty(value = "id")
	private List<Integer> toList;
}
