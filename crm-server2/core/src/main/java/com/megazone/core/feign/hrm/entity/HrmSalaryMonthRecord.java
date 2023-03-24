package com.megazone.core.feign.hrm.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("wk_hrm_salary_month_record")
@ApiModel(value = "HrmSalaryMonthRecord", description = "")
public class HrmSalaryMonthRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "s_record_id", type = IdType.AUTO)
	private Integer sRecordId;

	@ApiModelProperty
	private String title;

	@ApiModelProperty(value = "")
	private Integer year;

	@ApiModelProperty(value = "")
	private Integer month;

	@ApiModelProperty(value = "")
	private Integer num;

	@ApiModelProperty
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	@ApiModelProperty(value = "")
	private BigDecimal personalInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal personalProvidentFundAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateInsuranceAmount;

	@ApiModelProperty(value = "")
	private BigDecimal corporateProvidentFundAmount;

	@ApiModelProperty(value = "")
	private BigDecimal expectedPaySalary;

	@ApiModelProperty(value = "")
	private BigDecimal personalTax;

	@ApiModelProperty(value = "")
	private BigDecimal realPaySalary;

	@ApiModelProperty
	private String optionHead;

	@ApiModelProperty
	private Integer examineRecordId;
	@ApiModelProperty
	private Integer checkStatus;

	@ApiModelProperty
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
}
