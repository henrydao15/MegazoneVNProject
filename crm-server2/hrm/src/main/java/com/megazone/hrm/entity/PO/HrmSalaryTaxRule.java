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
@TableName("wk_hrm_salary_tax_rule")
@ApiModel(value = "HrmSalaryTaxRule", description = "")
public class HrmSalaryTaxRule implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "rule_id", type = IdType.AUTO)
	@ApiModelProperty
	private Integer ruleId;

	@ApiModelProperty(value = "")
	private String ruleName;

	@ApiModelProperty(value = " 1  2  3 ")
	private Integer taxType;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isTax;

	@ApiModelProperty(value = "")
	private Integer markingPoint;

	@ApiModelProperty(value = "()")
	private Integer decimalPoint;

	@ApiModelProperty(value = " 1 1211（） 2 112（）")
	private Integer cycleType;


	@ApiModelProperty
	@TableField(exist = false)
	private Integer salaryGroupCount;


}
