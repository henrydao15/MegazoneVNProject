package com.megazone.hrm.entity.VO;

import com.megazone.hrm.entity.PO.HrmAchievementSeg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AchievementTableTempVO {

	@ApiModelProperty
	private Integer tableId;

	@ApiModelProperty(value = "")
	private String tableName;

	@ApiModelProperty(value = "1 OKR 2 KPI")
	private Integer type;

	@ApiModelProperty(value = "")
	private String description;

	@ApiModelProperty
	private Integer isEmpWeight;


	@ApiModelProperty
	private List<HrmAchievementSeg> fixedSegListTemp;

	@ApiModelProperty
	private List<HrmAchievementSeg> noFixedSegListTemp;


}
