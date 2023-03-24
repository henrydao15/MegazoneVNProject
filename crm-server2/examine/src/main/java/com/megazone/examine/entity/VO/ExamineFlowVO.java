package com.megazone.examine.entity.VO;

import com.megazone.core.feign.admin.entity.SimpleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ExamineFlowVO {

	@ApiModelProperty
	private Integer flowId;

	@ApiModelProperty
	private Integer examineType;

	@ApiModelProperty
	private Integer rangeType;

	@ApiModelProperty
	private String name;

	@ApiModelProperty(value = " 1  2 ")
	private Integer examineErrorHandling;

	@ApiModelProperty(value = " 1  2  ")
	private Integer parentLevel;

	@ApiModelProperty(value = "ID")
	private Integer roleId;

	@ApiModelProperty
	private Integer type;

	@ApiModelProperty
	private Integer chooseType;

	@ApiModelProperty
	private List<SimpleUser> userList;

	@ApiModelProperty
	private List<ExamineFlowConditionVO> conditionList;
}
