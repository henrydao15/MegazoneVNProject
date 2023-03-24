package com.megazone.crm.entity.BO;

import com.megazone.crm.entity.PO.CrmField;
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
@ApiModel(value = "CrmField", description = "")
public class CrmFieldBO {

	@ApiModelProperty(value = " 1  2  3  4  5  6  78. 18")
	@NotNull
	private Integer label;

	@ApiModelProperty(value = "CrmField")
	private List<CrmField> data;
}
