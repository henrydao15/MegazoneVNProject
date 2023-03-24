package com.megazone.core.feign.jxc.entity;

import com.megazone.core.common.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author
 * @date 2020/9/7
 */
@Data
@ToString
@ApiModel(value = "JxcExamine", description = "")
public class JxcExamine {

	private Integer categoryType;

	@ApiModelProperty
	private Integer examineType;

	private JSONObject examineObj;

	@ApiModelProperty
	private Long ownerUserId;
}
