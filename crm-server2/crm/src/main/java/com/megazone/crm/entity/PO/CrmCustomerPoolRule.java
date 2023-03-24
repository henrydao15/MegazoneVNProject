package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.megazone.core.common.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@TableName("wk_crm_customer_pool_rule")
@ApiModel(value = "CrmCustomerPoolRule", description = "")
public class CrmCustomerPoolRule implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "rule_id", type = IdType.AUTO)
	private Integer ruleId;

	@ApiModelProperty(value = "id")
	private Integer poolId;

	@ApiModelProperty(value = " 1 2 3")
	private Integer type;

	@ApiModelProperty(value = " 0 1")
	private Integer dealHandle;

	@ApiModelProperty(value = " 0 1")
	private Integer businessHandle;

	@ApiModelProperty(value = " 1 2")
	private Integer customerLevelSetting;

	@ApiModelProperty(value = " 1")
	private String level;

	@ApiModelProperty(value = "ID")
	@TableField(exist = false)
	private List<JSONObject> levelSetting;

	@ApiModelProperty(value = "")
	private Integer limitDay;


}
