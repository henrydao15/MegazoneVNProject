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
 * @since 2020-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_marketing_info")
@ApiModel(value = "CrmMarketingInfo", description = "")
public class CrmMarketingInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "r_id", type = IdType.AUTO)
	private Integer rId;

	@ApiModelProperty(value = "ID")
	private Integer marketingId;

	@ApiModelProperty(value = "0  1  2")
	private Integer status;

	@ApiModelProperty(value = "")
	private String fieldInfo;

	@ApiModelProperty(value = "")
	private String device;

	@ApiModelProperty(value = "ID")
	private Long ownerUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
