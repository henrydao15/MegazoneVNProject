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
@TableName("wk_hrm_achievement_appraisal")
@ApiModel(value = "HrmAchievementAppraisal", description = "")
public class HrmAchievementAppraisal implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "appraisal_id", type = IdType.AUTO)
	private Integer appraisalId;

	@ApiModelProperty(value = "")
	private String appraisalName;

	@ApiModelProperty(value = "1  2  3  4 ")
	private Integer cycleType;

	@ApiModelProperty(value = "")
	private Date startTime;

	@ApiModelProperty(value = "")
	private Date endTime;

	@ApiModelProperty(value = "id")
	private Integer tableId;

	@ApiModelProperty(value = " 1 ")
	private Integer writtenBy;

	@ApiModelProperty(value = "")
	private String resultConfirmors;

	@ApiModelProperty(value = "")
	private BigDecimal fullScore;

	@ApiModelProperty(value = " 1  0 ")
	private Integer isForce;

	@ApiModelProperty(value = "")
	private String employeeIds;

	@ApiModelProperty(value = "")
	private String deptIds;
	@ApiModelProperty(value = " -1  0  1  2  3 ")
	private Integer appraisalSteps;
	@ApiModelProperty(value = " -1  0  1  2  3 ")
	private Integer activateSteps;
	@ApiModelProperty(value = " 0  1  2  3  4 ")
	private Integer status;

	@ApiModelProperty(value = " 0  1 ")
	private Integer isStop;

	@ApiModelProperty(value = "")
	private Date stopTime;


	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;


}
