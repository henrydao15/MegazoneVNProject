package com.megazone.work.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel
public class WorkTaskByLabelVO {
	@ApiModelProperty
	private Integer workId;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private String color;

	@ApiModelProperty
	private List<TaskInfoVO> list;
}
