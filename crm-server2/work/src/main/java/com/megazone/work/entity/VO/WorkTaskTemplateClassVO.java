package com.megazone.work.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wyq
 */
@Data
@Accessors(chain = true)
@ApiModel
public class WorkTaskTemplateClassVO {

	@ApiModelProperty
	private Integer classId;

	@ApiModelProperty
	private String className;

	@ApiModelProperty
	private Integer count;

	@ApiModelProperty
	private List<TaskInfoVO> list;
}
