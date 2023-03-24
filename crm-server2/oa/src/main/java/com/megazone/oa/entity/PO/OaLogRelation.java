package com.megazone.oa.entity.PO;

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
@TableName("wk_oa_log_relation")
@ApiModel(value = "OaLogRelation object", description = "Log related business table")
public class OaLogRelation implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Log associated business table")
	@TableId(value = "r_id", type = IdType.AUTO)
	private Integer rId;

	@ApiModelProperty(value = "Log ID")
	private Integer logId;

	@ApiModelProperty(value = "Customer IDs")
	private String customerIds;

	@ApiModelProperty(value = "Contact IDs")
	private String contactsIds;

	@ApiModelProperty(value = "Opportunity IDs")
	private String businessIds;

	@ApiModelProperty(value = "Contract IDs")
	private String contractIds;

	@ApiModelProperty(value = "Status 1 available")
	private Integer status;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
