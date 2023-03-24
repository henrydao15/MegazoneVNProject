package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class FiledListVO {
	@ApiModelProperty
	private Integer labelGroup;
	@ApiModelProperty
	private Date updateTime;
	@ApiModelProperty
	private String name;
}
