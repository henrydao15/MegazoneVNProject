package com.megazone.hrm.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Data
public class SimpleHrmEmployeeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String employeeName;

	@ApiModelProperty(value = "")
	private String deptName;

	@ApiModelProperty
	private String post;

	private String mobile;

	private String email;

	private Integer sex;

	@ApiModelProperty
	private Integer status;

	private Integer isDel;


}
