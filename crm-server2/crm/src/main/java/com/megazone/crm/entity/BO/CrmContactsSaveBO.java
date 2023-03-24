package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@ApiModel
public class CrmContactsSaveBO extends CrmModelSaveBO {
	@ApiModelProperty
	private Integer businessId;
}
