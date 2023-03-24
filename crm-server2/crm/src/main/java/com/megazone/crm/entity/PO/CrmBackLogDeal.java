package com.megazone.crm.entity.PO;

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
 * @since 2020-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_back_log_deal")
@ApiModel(value = "CrmBackLogDeal", description = "")
public class CrmBackLogDeal implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = " 1 2 3 4 5 6 7 8 9 10")
	private Integer model;

	@ApiModelProperty(value = "")
	private Integer crmType;

	@ApiModelProperty(value = "id")
	private Integer typeId;

	@ApiModelProperty(value = "id")
	private Integer poolId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;


}
