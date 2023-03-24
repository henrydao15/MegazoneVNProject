package com.megazone.work.entity.PO;

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
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_work_task_label")
@ApiModel(value = "WorkTaskLabel", description = "")
public class WorkTaskLabel implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "label_id", type = IdType.AUTO)
	private Integer labelId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	private Integer status;

	@ApiModelProperty(value = "")
	private String color;


}
