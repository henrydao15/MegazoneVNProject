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
@TableName("wk_work_task_relation")
@ApiModel(value = "WorkTaskRelation", description = "")
public class WorkTaskRelation implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	@TableId(value = "r_id", type = IdType.AUTO)
	private Integer rId;

	@ApiModelProperty(value = "ID")
	private Integer taskId;

	@ApiModelProperty(value = "IDs")
	private String customerIds;

	@ApiModelProperty(value = "IDs")
	private String contactsIds;

	@ApiModelProperty(value = "IDs")
	private String businessIds;

	@ApiModelProperty(value = "IDs")
	private String contractIds;

	@ApiModelProperty(value = "1")
	private Integer status;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
