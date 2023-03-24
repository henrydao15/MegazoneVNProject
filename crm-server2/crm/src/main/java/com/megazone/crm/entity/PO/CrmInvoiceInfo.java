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
 * @since 2020-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_invoice_info")
@ApiModel(value = "CrmInvoiceInfo", description = "")
public class CrmInvoiceInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "info_id", type = IdType.AUTO)
	private Integer infoId;

	@ApiModelProperty(value = "id")
	private Integer customerId;

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
	private String remark;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
