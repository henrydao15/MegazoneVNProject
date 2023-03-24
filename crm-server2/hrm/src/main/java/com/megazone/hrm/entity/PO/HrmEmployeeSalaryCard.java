package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_employee_salary_card")
@ApiModel(value = "HrmEmployeeSalaryCard", description = "")
public class HrmEmployeeSalaryCard implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "salary_card_id", type = IdType.AUTO)
	private Integer salaryCardId;

	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String salaryCardNum;

	@ApiModelProperty(value = "")
	private String accountOpeningCity;

	@ApiModelProperty(value = "")
	private String bankName;

	@ApiModelProperty(value = "")
	private String openingBank;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
