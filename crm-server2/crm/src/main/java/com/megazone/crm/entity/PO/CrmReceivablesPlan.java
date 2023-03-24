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
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_receivables_plan")
@ApiModel(value = "CrmReceivablesPlan", description = "")
public class CrmReceivablesPlan implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "receivables_plan_id", type = IdType.AUTO)
	private Integer receivablesPlanId;

	@ApiModelProperty(value = "")
	private String num;

	@ApiModelProperty(value = "ID")
	private Integer receivablesId;

	@ApiModelProperty(value = "1 0 ")
	private Integer status;

	@ApiModelProperty(value = "")
	private BigDecimal money;

	@ApiModelProperty(value = "")
	private Date returnDate;

	@ApiModelProperty(value = "")
	private String returnType;

	@ApiModelProperty(value = "")
	private Integer remind;

	@ApiModelProperty(value = "")
	private Date remindDate;

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

	@ApiModelProperty(value = "ID")
	private String batchId;

	@ApiModelProperty(value = "")
	private BigDecimal realReceivedMoney;

	@ApiModelProperty(value = "")
	private Date realReturnDate;

	@ApiModelProperty(value = "")
	private BigDecimal unreceivedMoney;

	@ApiModelProperty(value = " 0  1  2  3  4  5 ")
	private Integer receivedStatus;

	@ApiModelProperty(value = "ID")
	private Integer contractId;

	@ApiModelProperty(value = "ID")
	private Integer customerId;


	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String customerName;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String contractNum;


}
