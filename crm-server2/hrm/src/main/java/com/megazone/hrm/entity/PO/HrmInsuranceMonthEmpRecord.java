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
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_insurance_month_emp_record")
@ApiModel(value = "HrmInsuranceMonthEmpRecord", description = "")
public class HrmInsuranceMonthEmpRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "i_emp_record_id", type = IdType.AUTO)
	private Integer iEmpRecordId;

	@ApiModelProperty(value = "id")
	private Integer iRecordId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "id")
	private Integer schemeId;

	@ApiModelProperty(value = "")
	private Integer year;

	@ApiModelProperty(value = "")
	private Integer month;

	@ApiModelProperty(value = "")
	private BigDecimal personalInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal personalProvidentFundAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateProvidentFundAmount;

	@ApiModelProperty
	private Integer status;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
