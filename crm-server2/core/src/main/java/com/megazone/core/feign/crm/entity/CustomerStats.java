package com.megazone.core.feign.crm.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author
 * @date 2020/9/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "", description = "")
public class CustomerStats implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private Long id;

	@ApiModelProperty(value = "")
	private Long customerNum;

	@ApiModelProperty(value = " 0  1 ")
	private Integer dealStatus;

	@ApiModelProperty(value = "ID")
	private Long ownerUserId;

	@ApiModelProperty(value = "()")
	private String createDate;

	@ApiModelProperty(value = "()")
	private String dealDate;

}
