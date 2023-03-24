package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.feign.admin.entity.SimpleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@TableName("wk_crm_customer_setting")
@ApiModel(value = "CrmCustomerSetting", description = "")
public class CrmCustomerSetting implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "setting_id", type = IdType.AUTO)
	private Integer settingId;

	@ApiModelProperty(value = "")
	private String settingName;

	@ApiModelProperty(value = "")
	private Integer customerNum;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String range;

	@ApiModelProperty(value = " 0  1 ")
	private Integer customerDeal;

	@ApiModelProperty(value = " 1  2 ")
	private Integer type;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private List<SimpleDept> deptIds;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private List<SimpleUser> userIds;


}
