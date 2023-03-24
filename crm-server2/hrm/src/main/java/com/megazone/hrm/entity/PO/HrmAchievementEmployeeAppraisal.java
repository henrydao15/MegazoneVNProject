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
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_achievement_employee_appraisal")
@ApiModel(value = "HrmAchievementEmployeeAppraisal", description = "")
public class HrmAchievementEmployeeAppraisal implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "employee_appraisal_id", type = IdType.AUTO)
	private Integer employeeAppraisalId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "id")
	private Integer appraisalId;

	@ApiModelProperty(value = " 1  2  3  4  5 ")
	private Integer status;

	@ApiModelProperty(value = "")
	private BigDecimal score;

	@ApiModelProperty(value = "")
	private Integer levelId;

	@ApiModelProperty(value = " 0  1 ")
	private Integer readStatus;

	@ApiModelProperty(value = "id")
	private Integer followUpEmployeeId;

	@ApiModelProperty(value = "")
	private Integer followSort;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = " 0 1")
	private Integer isDraft;

}
