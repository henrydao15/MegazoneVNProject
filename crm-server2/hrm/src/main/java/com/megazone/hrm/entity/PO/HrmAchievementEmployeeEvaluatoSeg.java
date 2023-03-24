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
@TableName("wk_hrm_achievement_employee_evaluato_seg")
@ApiModel(value = "HrmAchievementEmployeeEvaluatoSeg", description = "")
public class HrmAchievementEmployeeEvaluatoSeg implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "employee_evaluato_seg_id", type = IdType.AUTO)
	private Integer employeeEvaluatoSegId;

	@ApiModelProperty(value = "id")
	private Integer employeeAppraisalId;

	@ApiModelProperty(value = "id")
	private Integer employeeEvaluatoId;

	@ApiModelProperty(value = "id")
	private Integer segId;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = "")
	private BigDecimal score;

	@ApiModelProperty(value = "")
	private String evaluate;

	private Integer status;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String employeeName;


}
