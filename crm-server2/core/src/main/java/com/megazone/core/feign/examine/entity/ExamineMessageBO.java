package com.megazone.core.feign.examine.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 * @date 2020/12/21
 */
@Data
public class ExamineMessageBO {

	private Integer categoryType;

	@ApiModelProperty
	private Integer examineType;

	private ExamineRecordLog examineLog;

	private Long ownerUserId;
}
