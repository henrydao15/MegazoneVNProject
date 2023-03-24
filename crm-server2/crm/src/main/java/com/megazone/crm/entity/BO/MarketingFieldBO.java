package com.megazone.crm.entity.BO;

import com.megazone.crm.entity.PO.CrmMarketingField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author
 * @date 2020/12/2
 */
@Data
@NoArgsConstructor
@ApiModel(value = "MarketingField", description = "")
public class MarketingFieldBO {

	@ApiModelProperty(value = "id")
	@NotNull
	private Integer formId;

	@ApiModelProperty(value = "CrmMarketingField")
	private List<CrmMarketingField> data;
}
