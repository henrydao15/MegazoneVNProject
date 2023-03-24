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
@TableName("wk_oa_event_relation")
@ApiModel(value = "OaEventRelation object", description = "Schedule related business table")
public class OaEventRelation implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Schedule related business table")
	@TableId(value = "eventrelation_id", type = IdType.AUTO)
	private Integer eventrelationId;

	@ApiModelProperty(value = "Schedule ID")
	private Integer eventId;

	@ApiModelProperty(value = "Customer IDs")
	private String customerIds;

	@ApiModelProperty(value = "Contact IDs")
	private String contactsIds;

	@ApiModelProperty(value = "Opportunity IDs")
	private String businessIds;

	@ApiModelProperty(value = "Contract IDs")
	private String contractIds;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
}
