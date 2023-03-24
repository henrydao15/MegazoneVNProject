package com.megazone.core.feign.crm.entity;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author bi
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
public class BiParams extends PageEntity {

	@ApiModelProperty
	private Integer deptId;

	@ApiModelProperty
	private Long userId;

	@ApiModelProperty
	private List<Long> userIds;

	@ApiModelProperty
	private String type;

	@ApiModelProperty
	private String startTime;

	@ApiModelProperty
	private String endTime;

	@ApiModelProperty
	private Integer isUser = 1;

	@ApiModelProperty
	private Integer typeId;

	@ApiModelProperty
	private Integer year;

	@ApiModelProperty
	private Integer menuId;

	@ApiModelProperty
	private Integer moneyType;

	@ApiModelProperty
	private Integer dataType;

	@ApiModelProperty
	private Integer queryType;

	@ApiModelProperty
	private Integer label;


	@ApiModelProperty
	private Integer order;

	@ApiModelProperty
	private String sortField;

	private Integer checkStatus;

	private Integer subUser;

	private String search;

	private Integer day;
}
