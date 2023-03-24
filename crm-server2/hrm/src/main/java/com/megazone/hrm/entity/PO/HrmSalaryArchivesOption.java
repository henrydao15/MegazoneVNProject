package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_salary_archives_option")
@ApiModel(value = "HrmSalaryArchivesOption", description = "")
public class HrmSalaryArchivesOption implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	private Integer isPro;

	@ApiModelProperty(value = "code")
	private Integer code;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String value;


}
