package com.megazone.admin.entity.BO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenameFileBO {

	@ApiModelProperty(value = "Attachment id")
	private Long fileId;

	@ApiModelProperty(value = "Attachment Name")
	private String name;
}
