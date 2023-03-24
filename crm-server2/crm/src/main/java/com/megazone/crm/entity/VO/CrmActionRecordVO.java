package com.megazone.crm.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "CrmActionRecordVO", description = "")
public class CrmActionRecordVO {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@ApiModelProperty(value = "ID")
	private Long createUserId;

	@ApiModelProperty(value = "")
	private Date createTime;

	@ApiModelProperty(value = "ip")
	private String ipAddress;

	@ApiModelProperty(value = "")
	private Integer types;

	@ApiModelProperty(value = "ID")
	private Integer actionId;

	@ApiModelProperty(value = "")
	private String object;

	@ApiModelProperty(value = "")
	private Integer behavior;

	@ApiModelProperty(value = "")
	private Object content;

	@ApiModelProperty(value = "")
	private String detail;


	@ApiModelProperty(value = "")
	private String realname;

	@ApiModelProperty(value = "")
	private String img;
}
