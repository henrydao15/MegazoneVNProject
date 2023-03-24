package com.megazone.core.feign.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@ApiModel
public class AdminMessageBO {

	@ApiModelProperty
	private Integer messageType;

	@ApiModelProperty
	private String title;

	@ApiModelProperty
	private String content;

	@ApiModelProperty
	private Integer typeId;

	@ApiModelProperty
	private Long userId;

	@ApiModelProperty
	private List<Long> ids;

	public void setIds(List<Long> ids) {
		HashSet<Long> hashSet = new HashSet<>(ids);
		this.ids = new ArrayList<>(hashSet);
	}
}
