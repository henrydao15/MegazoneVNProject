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
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_achievement_employee_result_confirmors")
@ApiModel(value = "HrmAchievementEmployeeResultConfirmors", description = "")
public class HrmAchievementEmployeeResultConfirmors implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "confirmors_id", type = IdType.AUTO)
	private Integer confirmorsId;

	private Integer employeeId;

	@ApiModelProperty(value = "id")
	private Integer appraisalId;

	@ApiModelProperty(value = "0  1 ")
	private Integer status;

	private Integer sort;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
