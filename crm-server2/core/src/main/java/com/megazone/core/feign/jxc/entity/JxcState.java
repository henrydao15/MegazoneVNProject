package com.megazone.core.feign.jxc.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-13
 */
@Data
@ToString
@ApiModel(value = "JxcState", description = "")
public class JxcState {
	@ApiModelProperty
	private Integer state;
	@ApiModelProperty
	private Long ownerUserId;
}
