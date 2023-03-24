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
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_customer_pool")
@ApiModel(value = "CrmCustomerPool", description = "")
public class CrmCustomerPool implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "pool_id", type = IdType.AUTO)
	private Integer poolId;

	@ApiModelProperty(value = "")
	private String poolName;

	@ApiModelProperty(value = " “,”")
	private String adminUserId;

	@ApiModelProperty(value = " “,”")
	private String memberUserId;

	@ApiModelProperty(value = " “,”")
	private String memberDeptId;

	@ApiModelProperty(value = " 0  1")
	private Integer status;

	@ApiModelProperty(value = " 0 1")
	private Integer preOwnerSetting;

	@ApiModelProperty(value = "")
	private Integer preOwnerSettingDay;

	@ApiModelProperty(value = " 0 1")
	private Integer receiveSetting;

	@ApiModelProperty(value = "")
	private Integer receiveNum;

	@ApiModelProperty(value = " 0 1")
	private Integer remindSetting;

	@ApiModelProperty(value = "")
	private Integer remindDay;

	@ApiModelProperty(value = " 0 1")
	private Integer putInRule;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
