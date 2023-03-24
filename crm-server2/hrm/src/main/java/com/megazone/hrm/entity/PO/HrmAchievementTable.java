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
@TableName("wk_hrm_achievement_table")
@ApiModel(value = "HrmAchievementTable", description = "")
public class HrmAchievementTable implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "table_id", type = IdType.AUTO)
	private Integer tableId;

	@ApiModelProperty(value = "")
	private String tableName;

	@ApiModelProperty(value = "1 OKR 2 KPI")
	private Integer type;

	@ApiModelProperty(value = "")
	private String description;

	@ApiModelProperty(value = " 1  0 ")
	private Integer status;

	@ApiModelProperty
	private Integer isEmpWeight;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;


}
