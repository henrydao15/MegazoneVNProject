package com.megazone.oa.entity.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class LogBO extends PageEntity {

	@ApiModelProperty
	private Integer by;

	@ApiModelProperty
	private String type;

	private Integer completeType;

	@ApiModelProperty
	private String createUserId;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startTime;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	@ApiModelProperty
	private List<Integer> deptIds;

	private Integer categoryId;

	private Integer crmType;
	private Integer order;
	private Integer today;
	private String search;
	private String sortField;
	private Integer logId;
}
