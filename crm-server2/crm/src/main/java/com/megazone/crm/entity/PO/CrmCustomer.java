package com.megazone.crm.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_customer")
@ApiModel(value = "CrmCustomer", description = "")
public class CrmCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "customer_id", type = IdType.AUTO)
	private Integer customerId;

	@ApiModelProperty(value = "")
	private String customerName;

	@ApiModelProperty(value = " 01")
	private Integer followup;

	@ApiModelProperty(value = "1")
	private Integer isLock;

	@ApiModelProperty(value = "")
	private Date nextTime;

	@ApiModelProperty(value = " 0  1 ")
	private Integer dealStatus;

	@ApiModelProperty(value = "")
	private Date dealTime;

	@ApiModelProperty(value = "ID")
	private Integer contactsId;

	@ApiModelProperty(value = "")
	private String mobile;

	@ApiModelProperty(value = "")
	private String telephone;

	@ApiModelProperty(value = "")
	private String website;

	@ApiModelProperty(value = "")
	private String email;

	@ApiModelProperty(value = "")
	private String remark;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "ID")
	private Long ownerUserId;

	@ApiModelProperty(value = "")
	private String address;

	@ApiModelProperty(value = "")
	private String location;

	@ApiModelProperty(value = "")
	private String detailAddress;

	@ApiModelProperty(value = "")
	private String lng;

	@ApiModelProperty(value = "")
	private String lat;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = " ")
	private String batchId;


	@ApiModelProperty(value = " 1  2 3")
	private Integer status;

	@ApiModelProperty(value = "")
	private Date lastTime;

	@ApiModelProperty(value = "")
	private Date poolTime;

	@ApiModelProperty(value = "1  2 ")
	private Integer isReceive;

	@ApiModelProperty(value = "")
	private String lastContent;

	@ApiModelProperty(value = "")
	private Date receiveTime;

	@ApiModelProperty(value = "id")
	private Long preOwnerUserId;


}
