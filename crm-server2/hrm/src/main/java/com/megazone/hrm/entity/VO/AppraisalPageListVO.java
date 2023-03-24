package com.megazone.hrm.entity.VO;

import com.megazone.hrm.entity.PO.HrmAchievementAppraisal;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AppraisalPageListVO extends HrmAchievementAppraisal {

	@ApiModelProperty
	private String tableName;

	@ApiModelProperty
	private String appraisalRange;

	@ApiModelProperty
	private Map<Integer, Long> statistics;

	@ApiModelProperty
	private Integer isArchive;


}
