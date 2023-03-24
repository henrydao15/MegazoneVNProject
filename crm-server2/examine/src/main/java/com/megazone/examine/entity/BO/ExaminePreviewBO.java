package com.megazone.examine.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author
 * @date 2020/12/15
 */
@Data
@ApiModel
public class ExaminePreviewBO {

	@ApiModelProperty(value = "0 OA 1  2  3 4 5  6 7 8  910 1112")
	private Integer label;

	@ApiModelProperty(value = "")
	private Map<String, Object> dataMap;

	@ApiModelProperty(value = "id")
	private Integer recordId;

	@ApiModelProperty(value = "id")
	private Long examineId;

	@ApiModelProperty(value = " 0 1")
	private Integer status;

	@ApiModelProperty(value = "")
	private Long ownerUserId;
}
