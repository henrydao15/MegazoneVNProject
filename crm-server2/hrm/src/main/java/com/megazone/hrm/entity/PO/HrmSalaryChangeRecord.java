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
 * @since 2020-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_salary_change_record")
@ApiModel(value = "HrmSalaryChangeRecord", description = "")
public class HrmSalaryChangeRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = " 1  2 ")
	private Integer recordType;

	@ApiModelProperty(value = " 0  1  2  3  4  5  6  7  8 ")
	private Integer changeReason;

	@ApiModelProperty(value = "")
	private Date enableDate;

	@ApiModelProperty(value = "")
	private String proBeforeSum;

	@ApiModelProperty(value = "")
	private String proAfterSum;

	@ApiModelProperty(value = "")
	private String proSalary;

	@ApiModelProperty(value = " json")
	private String beforeSum;

	@ApiModelProperty(value = "")
	private String afterSum;

	@ApiModelProperty(value = " json")
	private String salary;

	@ApiModelProperty(value = " 0  1  2 ")
	private Integer status;

	@ApiModelProperty
	private Integer employeeStatus;

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "")
	private String beforeTotal;

	@ApiModelProperty(value = "")
	private String afterTotal;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;


}
