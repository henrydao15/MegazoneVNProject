package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.megazone.core.common.RangeValidated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("wk_hrm_employee_work_experience")
@ApiModel(value = "HrmEmployeeWorkExperience", description = "")
@RangeValidated(minFieldName = "workStartTime", maxFieldName = "workEndTime")
public class HrmEmployeeWorkExperience implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "work_exp_id", type = IdType.AUTO)
	private Integer workExpId;

	private Integer employeeId;

	@ApiModelProperty(value = "")
	private String workUnit;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date workStartTime;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date workEndTime;

	@ApiModelProperty(value = "")
	private String leavingReason;

	@ApiModelProperty(value = "")
	private String witness;

	@ApiModelProperty(value = "")
	private String witnessPhone;

	@ApiModelProperty(value = "")
	private String workRemarks;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	private Integer sort;


}
