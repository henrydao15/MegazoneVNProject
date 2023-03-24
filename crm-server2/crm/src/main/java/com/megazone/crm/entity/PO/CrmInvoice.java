package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_invoice")
@ApiModel(value = "CrmInvoice", description = "")
public class CrmInvoice implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "invoice_id", type = IdType.AUTO)
	private Integer invoiceId;

	@ApiModelProperty(value = "")
	private String invoiceApplyNumber;

	@ApiModelProperty(value = "id")
	private Integer customerId;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String customerName;

	@ApiModelProperty(value = "id")
	private Integer contractId;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String contractNum;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String contractMoney;

	@ApiModelProperty(value = "")
	private BigDecimal invoiceMoney;

	@ApiModelProperty(value = "")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date invoiceDate;

	@ApiModelProperty(value = "")
	private Integer invoiceType;

	@ApiModelProperty(value = "")
	private String remark;

	@ApiModelProperty(value = " 1 2")
	private Integer titleType;

	@ApiModelProperty(value = "")
	private String invoiceTitle;

	@ApiModelProperty(value = "")
	private String taxNumber;

	@ApiModelProperty(value = "")
	private String depositBank;

	@ApiModelProperty(value = "")
	private String depositAccount;

	@ApiModelProperty(value = "")
	private String depositAddress;

	@ApiModelProperty(value = "")
	private String telephone;

	@ApiModelProperty(value = "")
	private String contactsName;

	@ApiModelProperty(value = "")
	private String contactsMobile;

	@ApiModelProperty(value = "")
	private String contactsAddress;

	@ApiModelProperty(value = "id")
	private Integer examineRecordId;

	@ApiModelProperty(value = " 0、1、2、3、4")
	private Integer checkStatus;

	@ApiModelProperty(value = "id")
	private Long ownerUserId;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String ownerUserName;

	@ApiModelProperty(value = "")
	@TableField(exist = false)
	private String createUserName;

	@ApiModelProperty(value = "")
	private String invoiceNumber;

	@ApiModelProperty(value = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+9", pattern = "yyyy-MM-dd")
	private Date realInvoiceDate;

	@ApiModelProperty(value = "")
	private String logisticsNumber;

	@ApiModelProperty(value = "")
	private Integer invoiceStatus;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@TableField(exist = false)
	@ApiModelProperty(value = "ID")
	private Long checkUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "id")
	private String batchId;


}
