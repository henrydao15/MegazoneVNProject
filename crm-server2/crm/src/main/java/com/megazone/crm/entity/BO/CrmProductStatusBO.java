package com.megazone.crm.entity.BO;

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
@ApiModel(value = "BO")
public class CrmProductStatusBO {
	@ApiModelProperty
	@NotNull
	private List<Integer> ids;

	@ApiModelProperty
	@NotNull
	private Integer status;
}
