package com.megazone.core.feign.examine.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date 2020/12/17
 */
@Data
public class ExamineGeneralBO {

	@ApiModelProperty
	private Integer flowId;

	@ApiModelProperty
	private List<Long> userList;

	@ApiModelProperty
	private List<Integer> deptList;

	@ApiModelProperty
	private List<Integer> roleList;
}
