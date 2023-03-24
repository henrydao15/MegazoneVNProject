package com.megazone.admin.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class HrmSimpleUserVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty
	private Long userId;

	@ApiModelProperty
	private String img;

	@ApiModelProperty
	private String realname;

	@ApiModelProperty
	private String post;

	@ApiModelProperty
	private String mobile;

	@ApiModelProperty
	private Integer sex;
}
