package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author
 */
@Data
@ToString
@ApiModel
public class CrmRelevanceBusinessBO {

	@ApiModelProperty
	private Integer businessId;

	@ApiModelProperty
	private List<Integer> contactsIds;
}
