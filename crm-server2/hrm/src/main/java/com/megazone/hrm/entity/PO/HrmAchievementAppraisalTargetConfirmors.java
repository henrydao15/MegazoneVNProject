package com.megazone.hrm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("wk_hrm_achievement_appraisal_target_confirmors")
@ApiModel(value = "HrmAchievementAppraisalTargetConfirmors", description = "")
public class HrmAchievementAppraisalTargetConfirmors implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "target_confirmors_id", type = IdType.AUTO)
	private Integer targetConfirmorsId;

	@ApiModelProperty(value = "id")
	private Integer appraisalId;

	@ApiModelProperty(value = "1  2  3  4  5 ")
	private Integer type;

	@ApiModelProperty(value = "id")
	private Integer employeeId;

	@ApiModelProperty(value = " ")
	private Integer sort;


	@TableField(exist = false)
	private String employeeName;

	@TableField(exist = false)
	private Integer status;


}
