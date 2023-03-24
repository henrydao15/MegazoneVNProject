package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("wk_hrm_insurance_month_emp_project_record")
@ApiModel(value = "HrmInsuranceMonthEmpProjectRecord", description = "")
public class HrmInsuranceMonthEmpProjectRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "emp_project_record_id", type = IdType.AUTO)
	private Integer empProjectRecordId;

	@ApiModelProperty(value = "id")
	private Integer iEmpRecordId;

	@ApiModelProperty(value = "id")
	private Integer projectId;

	@ApiModelProperty(value = "1  2  3  4  5  6  7  8 ")
	private Integer type;

	@ApiModelProperty(value = "")
	private String projectName;

	@ApiModelProperty(value = "")
	private BigDecimal defaultAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateProportion;

	@ApiModelProperty(value = "")
	private BigDecimal personalProportion;

	@ApiModelProperty(value = "")
	private BigDecimal corporateAmount;

	@ApiModelProperty(value = "")
	private BigDecimal personalAmount;


}
