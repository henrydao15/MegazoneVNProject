package com.megazone.core.feign.examine.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
@NoArgsConstructor
public class ExamineRecordReturnVO implements Serializable {

	@ApiModelProperty
	private Integer recordId;

	@ApiModelProperty
	private Integer examineStatus;

	@ApiModelProperty
	private List<Integer> examineLogIds;

	@ApiModelProperty
	private List<Long> examineUserIds;


	public ExamineRecordReturnVO(Integer recordId, Integer examineStatus, List<Integer> examineLogIds) {
		this.recordId = recordId;
		this.examineStatus = examineStatus;
		this.examineLogIds = examineLogIds;
	}
}
