package com.megazone.work.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wyq
 */
@Data
@ApiModel
@Accessors(chain = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MyTaskVO {

	@ApiModelProperty
	private String title;

	@ApiModelProperty
	private Integer isTop;

	@ApiModelProperty
	private Integer count;

	@ApiModelProperty
	private List<TaskInfoVO> list;
}
