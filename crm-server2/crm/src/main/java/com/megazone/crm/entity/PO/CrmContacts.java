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
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_crm_contacts")
@ApiModel(value = "CrmContacts", description = "")
public class CrmContacts implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "contacts_id", type = IdType.AUTO)
	private Integer contactsId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private Date nextTime;

	@ApiModelProperty(value = "")
	private String mobile;

	@ApiModelProperty(value = "")
	private String telephone;

	@ApiModelProperty(value = "")
	private String email;

	@ApiModelProperty(value = "")
	private String post;

	@ApiModelProperty(value = "ID")
	private Integer customerId;

	@ApiModelProperty(value = "")
	private String address;

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
	private String batchId;


	@ApiModelProperty(value = "")
	private Date lastTime;

	@TableField(exist = false)
	private String policymakers;


}
