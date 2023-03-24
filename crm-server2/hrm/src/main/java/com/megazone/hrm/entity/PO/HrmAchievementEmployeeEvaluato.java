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
@TableName("wk_hrm_achievement_employee_evaluato")
@ApiModel(value = "HrmAchievementEmployeeEvaluato", description = "")
public class HrmAchievementEmployeeEvaluato implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "employee_evaluato_id", type = IdType.AUTO)
	private Integer employeeEvaluatoId;

	@ApiModelProperty(value = "id")
	private Integer employeeAppraisalId;

	@ApiModelProperty(value = "id")
	private Integer appraisalId;

	@ApiModelProperty(value = "")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private BigDecimal weight;

	@ApiModelProperty(value = "")
	private BigDecimal score;

	@ApiModelProperty(value = "")
	private Integer levelId;

	@ApiModelProperty(value = "")
	private String evaluate;

	@ApiModelProperty(value = "")
	private String rejectReason;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	private Integer sort;

	@ApiModelProperty(value = "0  1  2  3 ")
	private Integer status;


}
