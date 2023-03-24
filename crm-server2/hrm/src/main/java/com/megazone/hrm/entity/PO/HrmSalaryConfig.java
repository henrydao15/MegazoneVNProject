package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_salary_config")
@ApiModel(value = "HrmSalaryConfig", description = "")
public class HrmSalaryConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "config_id", type = IdType.AUTO)
	private Integer configId;

	@ApiModelProperty(value = "")
	private Integer salaryCycleStartDay;

	@ApiModelProperty(value = "")
	private Integer salaryCycleEndDay;

	@ApiModelProperty(value = " 1  2 ")
	private Integer payType;

	@ApiModelProperty(value = "")
	private Integer payDay;

	@ApiModelProperty(value = " 0 1 2")
	private Integer socialSecurityMonthType;

	@ApiModelProperty(value = "（2020.05）")
	private String salaryStartMonth;

	@ApiModelProperty(value = "（2020.05）")
	private String socialSecurityStartMonth;


	@ApiModelProperty
	@TableField(exist = false)
	private Integer lastSocialSecurityYear;

	@ApiModelProperty
	@TableField(exist = false)
	private Integer lastSalaryYear;

	@ApiModelProperty
	@TableField(exist = false)
	private Integer lastSalaryMonth;

	@ApiModelProperty
	@TableField(exist = false)
	private String lastSalarySlipMonth;


}
