package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Administrator
 */
@Data
@ToString
@ApiModel
public class CrmFirstContactsBO {

	@ApiModelProperty
	private Integer contactsId;

	@ApiModelProperty
	private Integer customerId;

	@ApiModelProperty
	private Integer businessId;
}
