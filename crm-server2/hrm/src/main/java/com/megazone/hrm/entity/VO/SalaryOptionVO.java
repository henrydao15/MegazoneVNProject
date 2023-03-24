package com.megazone.hrm.entity.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@Data
public class SalaryOptionVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Integer code;

	@ApiModelProperty(value = "")
	private Integer parentCode;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isFixed;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isPlus;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isTax;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isShow;

	@ApiModelProperty
	private Integer isCompute;

	@ApiModelProperty
	private Integer isOpen;


	@ApiModelProperty
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<SalaryOptionVO> children;


}
