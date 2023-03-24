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
@TableName("wk_work_collect")
@ApiModel(value = "WorkCollect", description = "")
public class WorkCollect implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "collect_id", type = IdType.AUTO)
	private Integer collectId;

	@ApiModelProperty(value = "id")
	private Integer workId;

	@ApiModelProperty(value = "id")
	private Long userId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
