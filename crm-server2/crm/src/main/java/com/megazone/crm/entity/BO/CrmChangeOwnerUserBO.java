package com.megazone.crm.entity.BO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Data
@ToString
@ApiModel(value = "BO")
public class CrmChangeOwnerUserBO {

	@ApiModelProperty
	private List<Integer> ids;

	@ApiModelProperty
	private Long ownerUserId;

	@ApiModelProperty
	private Integer transferType;

	@ApiModelProperty
	private Integer power;

	@ApiModelProperty
	private List<Integer> changeType;

	@ApiModelProperty
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date expiresTime;
}
