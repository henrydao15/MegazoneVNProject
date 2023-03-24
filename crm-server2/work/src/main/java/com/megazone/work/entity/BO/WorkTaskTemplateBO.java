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
public class WorkTaskTemplateBO {
	@ApiModelProperty
	@NotNull
	private Integer workId;

	@ApiModelProperty
	private List<Integer> labelId;

	@ApiModelProperty
	private List<Long> mainUserId;

	@ApiModelProperty
	private Integer stopTimeType;

	@ApiModelProperty
	private String taskName;

	@ApiModelProperty
	private Integer sort;

	@ApiModelProperty
	private Boolean completedTask;
}
