package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotesVO {

	private Integer notesId;

	@ApiModelProperty
	private Integer type;

	@ApiModelProperty
	private String content;

	@ApiModelProperty
	private Integer typeId;
}
