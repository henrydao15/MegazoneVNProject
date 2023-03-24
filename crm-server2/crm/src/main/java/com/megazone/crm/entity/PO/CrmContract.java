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
import java.util.List;

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
@TableName("wk_crm_contract")
@ApiModel(value = "CrmContract", description = "")
public class CrmContract implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "contract_id", type = IdType.AUTO)
	private Integer contractId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "ID")
	private Integer customerId;

	@ApiModelProperty(value = "ID")
	private Integer businessId;

	@ApiModelProperty(value = "0、1、2、3 4: 5  6  7  8 ")
	private Integer checkStatus;

	@ApiModelProperty(value = "ID")
	private Integer examineRecordId;

	@ApiModelProperty(value = "")
	private Date orderDate;

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

	@ApiModelProperty(value = "")
	private String num;

	@ApiModelProperty(value = "")
	private Date startTime;

	@ApiModelProperty(value = "")
	private Date endTime;

	@ApiModelProperty(value = "")
	private BigDecimal money;

	@ApiModelProperty(value = "")
	private BigDecimal discountRate;

	@ApiModelProperty(value = "")
	private BigDecimal totalPrice;

	@ApiModelProperty(value = "")
	private String types;

	@ApiModelProperty(value = "")
	private String paymentType;

	@ApiModelProperty(value = " ")
	private String batchId;

	@ApiModelProperty(value = "（id）")
	private Integer contactsId;

	@ApiModelProperty(value = "")
	private String remark;

	@ApiModelProperty(value = "")
	private String companyUserId;


	@ApiModelProperty(value = "")
	private Date lastTime;

	private BigDecimal receivedMoney;

	private BigDecimal unreceivedMoney;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private List<CrmContractProduct> list;
}
