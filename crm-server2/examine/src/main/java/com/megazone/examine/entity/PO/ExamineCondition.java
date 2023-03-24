package com.megazone.examine.entity.PO;

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
 * @since 2020-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_examine_condition")
@ApiModel(value = "ExamineCondition", description = "")
public class ExamineCondition implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "condition_id", type = IdType.AUTO)
	private Integer conditionId;

	@ApiModelProperty(value = "")
	private String conditionName;

	@ApiModelProperty(value = "ID")
	private Integer flowId;

	@ApiModelProperty(value = " ")
	private Integer priority;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "ID")
	private String batchId;


}
