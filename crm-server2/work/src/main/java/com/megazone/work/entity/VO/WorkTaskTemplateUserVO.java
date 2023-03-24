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
public class WorkTaskTemplateUserVO {
	@ApiModelProperty
	private Long userId;

	@ApiModelProperty
	private String realname;

	@ApiModelProperty
	private String img;

	@ApiModelProperty
	private Integer count;

	@ApiModelProperty
	private List<TaskInfoVO> list;
}
