package com.megazone.oa.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExaminePageBO extends PageEntity {

	@ApiModelProperty
	private Integer categoryId;

	private Integer status;

	private Date startTime;

	private Date endTime;
}
