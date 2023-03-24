package com.megazone.examine.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2020/12/18
 */
@Data
public class ExamineFlowDataVO {

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
	private List<Map<String, Object>> userList;

	private Integer examineStatus;
}
