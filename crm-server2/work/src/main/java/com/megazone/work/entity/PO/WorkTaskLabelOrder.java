package com.megazone.work.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
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
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_work_task_label_order")
@ApiModel(value = "WorkTaskLabelOrder", description = "")
public class WorkTaskLabelOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "order_id", type = IdType.AUTO)
	private Integer orderId;

	private Integer labelId;

	private Long userId;

	private Integer orderNum;


}
