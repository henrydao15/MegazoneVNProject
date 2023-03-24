package com.megazone.examine.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ExamineSaveBO {


	@ApiModelProperty(value = "ID")
	private Long examineId;

	@ApiModelProperty(value = "0 OA 1  2  3 4 5  6 7 8  910 1112")
	private Integer label;

	@ApiModelProperty(value = "")
	private String examineName;

	@ApiModelProperty
	private String examineIcon;

	@ApiModelProperty(value = " 1  2 ")
	private Integer recheckType;

	@ApiModelProperty(value = "")
	private List<Long> userList;

	@ApiModelProperty(value = "")
	private List<Integer> deptList;

	@ApiModelProperty(value = "")
	private List<Long> managerList;

	@ApiModelProperty(value = "1  2  3  ")
	private Integer status = 1;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "1  2  3  4  5  6  0 ")
	private Integer oaType;

	@ApiModelProperty(value = "")
	private List<ExamineDataSaveBO> dataList;
}
