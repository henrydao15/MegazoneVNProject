package com.megazone.work.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wyq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class OaTaskListBO extends PageEntity {

	@ApiModelProperty
	private Integer type;

	@ApiModelProperty
	private Integer status;

	@ApiModelProperty
	private Integer priority;

	@ApiModelProperty
	private String dueDate;

	@ApiModelProperty
	private Integer mold;

	@ApiModelProperty
	private Long userId;

	@ApiModelProperty
	private Long mainUserId;

	@ApiModelProperty
	private List<Long> mainUserIds;

	@ApiModelProperty
	private String search;

	private Boolean isExport;
}
