package com.megazone.hrm.entity.BO;

import com.megazone.core.feign.examine.entity.ExamineRecordSaveBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitExamineBO {

	@ApiModelProperty
	private Integer sRecordId;

	@ApiModelProperty
	private Long checkUserId;

	private Integer examineRecordId;

	private Integer checkStatus;

	@ApiModelProperty
	private ExamineRecordSaveBO examineFlowData;
}
