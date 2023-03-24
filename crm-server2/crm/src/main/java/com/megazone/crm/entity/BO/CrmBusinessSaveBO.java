package com.megazone.crm.entity.BO;

import com.megazone.crm.entity.PO.CrmBusinessProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@ApiModel
public class CrmBusinessSaveBO extends CrmModelSaveBO {
	@ApiModelProperty
	private List<CrmBusinessProduct> product = new ArrayList<>();

	@ApiModelProperty
	private Integer contactsId;
}
