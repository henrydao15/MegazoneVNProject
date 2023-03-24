package com.megazone.core.feign.examine.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-12-04
 */
@Data
public class ExamineRecordLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer logId;

	@ApiModelProperty(value = "ID")
	private Long examineId;

	@ApiModelProperty(value = "ID")
	private Integer flowId;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "ID")
	private Integer recordId;

	@ApiModelProperty(value = "1  2  3 ")
	private Integer type;

	@ApiModelProperty(value = "")
	private Integer sort;

	@ApiModelProperty(value = "0、1、2、3 4: 5  6  7  8 ")
	private Integer examineStatus;

	@ApiModelProperty(value = "ID")
	private Long examineUserId;

	@ApiModelProperty(value = "ID")
	private Integer examineRoleId;

	@ApiModelProperty(value = "ID")
	private Long createUserId;

	@ApiModelProperty(value = "ID")
	private String batchId;


}
