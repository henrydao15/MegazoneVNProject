package com.megazone.crm.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author
 */
@Data
@ToString
@ApiModel
public class CrmMembersSelectVO {

	@ApiModelProperty
	private Long userId;

	@ApiModelProperty
	private String realname;

	@ApiModelProperty
	private String deptName;

	@ApiModelProperty
	private Integer power;

	@ApiModelProperty
	private String expiresTime;
}
