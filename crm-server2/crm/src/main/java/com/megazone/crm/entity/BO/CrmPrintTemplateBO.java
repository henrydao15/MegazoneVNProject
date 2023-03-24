package com.megazone.crm.entity.BO;

import com.megazone.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
public class CrmPrintTemplateBO extends PageEntity {

	@ApiModelProperty
	private Integer type;

}
