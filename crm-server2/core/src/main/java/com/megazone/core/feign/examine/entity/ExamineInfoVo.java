package com.megazone.core.feign.examine.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-18
 */
@Data
@ApiModel(value = "Examine", description = "")
public class ExamineInfoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private Long examineId;

	@ApiModelProperty(value = "initId")
	private Long examineInitId;

	@ApiModelProperty(value = "0 OA 1  2  3 4 5  6 7 8  910 1112")
	private Integer label;

	@ApiModelProperty(value = "")
	private String examineIcon;

	@ApiModelProperty(value = "")
	private String examineName;

	@ApiModelProperty(value = " 1  2 ")
	private Integer recheckType;

	@ApiModelProperty(value = "")
	private Long createUserId;

	@ApiModelProperty(value = "1  2  3  ")
	private Integer status;

	@ApiModelProperty(value = "ID")
	private String batchId;


	@ApiModelProperty(value = "")
	private Long updateUserId;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "（）")
	private String userIds;

	@ApiModelProperty(value = "（）")
	private String deptIds;

	@ApiModelProperty(value = "1  2  3  4  5  6  0 ")
	private Integer oaType;


}
