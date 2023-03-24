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
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_hrm_salary_group")
@ApiModel(value = "HrmSalaryGroup", description = "")
public class HrmSalaryGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "group_id", type = IdType.AUTO)
	private Integer groupId;

	@ApiModelProperty(value = "")
	private String groupName;

	@ApiModelProperty(value = "")
	private String deptIds;

	@ApiModelProperty(value = "")
	private String employeeIds;

	@ApiModelProperty(value = "")
	private String salaryStandard;

	@ApiModelProperty(value = "、")
	private String changeRule;

	@ApiModelProperty(value = "id")
	private Integer ruleId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;


}
