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
@TableName("wk_examine_flow")
@ApiModel(value = "ExamineFlow", description = "")
public class ExamineFlow implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "flow_id", type = IdType.AUTO)
	private Integer flowId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "ID")
	private Long examineId;

	@ApiModelProperty(value = "0  1  2  3  4  5 ")
	private Integer examineType;

	@ApiModelProperty(value = " 1  2 ")
	private Integer examineErrorHandling;

	@ApiModelProperty(value = "ID")
	private Integer conditionId;

	@ApiModelProperty(value = "，")
	private Integer sort;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "ID")
	private String batchId;


}
