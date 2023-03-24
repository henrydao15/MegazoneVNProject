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
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_salary_month_emp_record")
@ApiModel(value = "HrmSalaryMonthEmpRecord", description = "")
public class HrmSalaryMonthEmpRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "s_emp_record_id", type = IdType.AUTO)
	private Integer sEmpRecordId;

	@ApiModelProperty(value = "id")
	private Integer sRecordId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private BigDecimal actualWorkDay;

	@ApiModelProperty(value = "")
	private BigDecimal needWorkDay;

	@ApiModelProperty(value = "")
	private Integer year;

	@ApiModelProperty(value = "")
	private Integer month;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
