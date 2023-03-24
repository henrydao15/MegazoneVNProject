package com.megazone.crm.entity.BO;

import com.megazone.crm.entity.PO.CrmFieldSort;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author
 */
@Data
@ToString
@ApiModel(value = "CrmFieldSort", description = "")
public class CrmFieldSortBO {

	@ApiModelProperty(value = "")
	private List<CrmFieldSort> noHideFields;

	@ApiModelProperty(value = "")
	private List<CrmFieldSort> hideFields;

	@NotNull
	@ApiModelProperty(value = "label", required = true)
	private Integer label;
}
