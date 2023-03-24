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
@TableName("wk_crm_return_visit")
@ApiModel(value = "CrmReturnVisit", description = "")
public class CrmReturnVisit implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@TableId(value = "visit_id", type = IdType.AUTO)
	private Integer visitId;

	@ApiModelProperty(value = "")
	private String visitNumber;

	@ApiModelProperty(value = "")
	private Date visitTime;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long ownerUserId;

	@ApiModelProperty(value = "id")
	private Integer customerId;

	@ApiModelProperty(value = "id")
	private Integer contractId;

	@ApiModelProperty(value = "id")
	private Integer contactsId;

	@ApiModelProperty(value = "id")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "id")
	private String batchId;


}
