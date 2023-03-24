package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("wk_crm_business")
@ApiModel(value = "CrmBusiness", description = "")
public class CrmBusiness implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "business_id", type = IdType.AUTO)
	private Integer businessId;

	@ApiModelProperty(value = "")
	private Integer typeId;

	@ApiModelProperty(value = "")
	private Integer statusId;

	@ApiModelProperty(value = "")
	private Date nextTime;

	@ApiModelProperty(value = "ID")
	private Integer customerId;

	@ApiModelProperty(value = "ID")
	private Integer contactsId;

	@ApiModelProperty(value = "")
	private Date dealDate;

	@ApiModelProperty(value = " 01")
	private Integer followup;

	@ApiModelProperty(value = "")
	private String businessName;

	@ApiModelProperty(value = "")
	private BigDecimal money;

	@ApiModelProperty(value = "")
	private BigDecimal discountRate;

	@ApiModelProperty(value = "")
	private BigDecimal totalPrice;

	@ApiModelProperty(value = "")
	private String remark;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "ID")
	private Long ownerUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = " ")
	private String batchId;

	@ApiModelProperty(value = "123")
	private Integer isEnd;

	private String statusRemark;

	@ApiModelProperty(value = "1 3  ")
	private Integer status;

	@ApiModelProperty(value = "")
	private Date lastTime;


}
