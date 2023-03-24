package com.megazone.crm.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-19
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CrmFieldSort", description = "")
public class CrmFieldSortVO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "ID")
	private Integer id;

	@ApiModelProperty(value = "ID")
	private Integer fieldId;

	@ApiModelProperty(value = "")
	private String fieldName;

	@ApiModelProperty(value = "")
	private String formType;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private Integer type;

	@ApiModelProperty(value = "")
	private Integer width;


}
