package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_salary_slip")
@ApiModel(value = "HrmSalarySlip", description = "")
public class HrmSalarySlip implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "id")
	private Integer recordId;

	private Integer sEmpRecordId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	private Integer year;

	private Integer month;

	@ApiModelProperty(value = " 0  1 ")
	private Integer readStatus;

	@ApiModelProperty(value = "")
	private BigDecimal realSalary;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;


}
