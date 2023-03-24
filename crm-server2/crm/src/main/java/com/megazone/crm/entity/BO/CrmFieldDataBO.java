package com.megazone.crm.entity.BO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * crm
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "crm", description = "crm")
public class CrmFieldDataBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private Integer id;

	@ApiModelProperty(value = "Id")
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String value;

	@ApiModelProperty(value = "batchId")
	private String batchId;

	@ApiModelProperty(value = "ID")
	private String typeId;

	@ApiModelProperty(value = "")
	private Integer type;

}
