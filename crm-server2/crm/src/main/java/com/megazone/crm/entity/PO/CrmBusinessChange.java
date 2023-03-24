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
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_business_change")
@ApiModel(value = "CrmBusinessChange", description = "")
public class CrmBusinessChange implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "change_id", type = IdType.AUTO)
	private Integer changeId;

	@ApiModelProperty(value = "ID")
	private Integer businessId;

	@ApiModelProperty(value = "ID")
	private Integer statusId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;


}
