package com.megazone.oa.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_examine")
@ApiModel(value = "OaExamine Object", description = "Approval Form")
public class OaExamine implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "examine_id", type = IdType.AUTO)
	private Integer examineId;

	@ApiModelProperty(value = "Approval Type")
	private Integer categoryId;

	@ApiModelProperty(value = "content")
	private String content;

	@ApiModelProperty(value = "remarks")
	private String remark;

	@ApiModelProperty(value = "Leave type")
	private String typeId;

	@ApiModelProperty(value = "Total amount of travel, reimbursement")
	private BigDecimal money;

	@ApiModelProperty(value = "start time")
	private Date startTime;

	@ApiModelProperty(value = "end time")
	private Date endTime;

	@ApiModelProperty(value = "Duration")
	private BigDecimal duration;

	@ApiModelProperty(value = "Creator ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "update time")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "Attachment batch id")
	private String batchId;


	@ApiModelProperty(value = "Audit Record ID")
	private Integer examineRecordId;

	@ApiModelProperty
	private Integer examineStatus;

}
