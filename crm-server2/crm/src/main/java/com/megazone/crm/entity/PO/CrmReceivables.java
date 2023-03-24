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
@TableName("wk_crm_receivables")
@ApiModel(value = "CrmReceivables", description = "")
public class CrmReceivables implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "receivables_id", type = IdType.AUTO)
	private Integer receivablesId;

	@ApiModelProperty(value = "")
	private String number;

	@ApiModelProperty(value = "ID")
	private Integer receivablesPlanId;

	@ApiModelProperty(value = "ID")
	private Integer customerId;

	@ApiModelProperty(value = "ID")
	private Integer contractId;

	@ApiModelProperty(value = "0、1、2、3 4: 5 ")
	private Integer checkStatus;

	@ApiModelProperty(value = "ID")
	private Integer examineRecordId;

	@ApiModelProperty(value = "")
	private Date returnTime;

	@ApiModelProperty(value = "")
	private String returnType;

	@ApiModelProperty(value = "")
	private BigDecimal money;

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

	@ApiModelProperty(value = "")
	private String remarks;

	@ApiModelProperty(value = "")
	private String batchId;


}
