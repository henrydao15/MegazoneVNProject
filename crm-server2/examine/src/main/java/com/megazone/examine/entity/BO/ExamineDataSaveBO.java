package com.megazone.examine.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ExamineDataSaveBO {

	@ApiModelProperty
	private Integer examineType;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private Integer examineErrorHandling;

	@ApiModelProperty(" 1  2  3   \n" +
			" ， 0  1  \n" +
			" 1  2  ")
	private Integer type;

	@ApiModelProperty
	private Integer rangeType;

	@ApiModelProperty
	private Integer chooseType;

	@ApiModelProperty
	private List<Long> userList;

	@ApiModelProperty
	private List<Integer> deptList;

	@ApiModelProperty
	private Integer roleId;

	@ApiModelProperty(" 1  2  \n" +
			"")
	private Integer parentLevel;

	@ApiModelProperty
	private List<ExamineConditionSaveBO> conditionList;
}
