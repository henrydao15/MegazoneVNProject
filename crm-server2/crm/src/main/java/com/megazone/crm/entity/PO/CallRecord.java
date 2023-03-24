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
 * @author Ian
 * @date 2020/8/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_call_record")
@ApiModel(value = "CallRecord", description = "")
public class CallRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "call_record_id", type = IdType.AUTO)
	private Integer callRecordId;

	@ApiModelProperty(value = "")
	private String number;

	@ApiModelProperty(value = "")
	private Date startTime;

	@ApiModelProperty(value = "")
	private Date answerTime;

	@ApiModelProperty(value = "")
	private Date endTime;

	@ApiModelProperty(value = "()")
	private Integer talkTime;

	@ApiModelProperty(value = "")
	private Integer dialTime;

	@ApiModelProperty(value = " (0，1，2，3)")
	private Integer state;

	@ApiModelProperty(value = " (0，1)")
	private Integer type;

	@ApiModelProperty(value = " leads，customer，contacts")
	private String model;

	@ApiModelProperty(value = "ID")
	private Integer modelId;

	@ApiModelProperty(value = "")
	private String filePath;

	@ApiModelProperty(value = "")
	private Integer size;

	@ApiModelProperty(value = "")
	private String fileName;

	@ApiModelProperty(value = "0：CRM; 1：")
	private Integer callUpload;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "ID")
	private Long ownerUserId;

	@ApiModelProperty(value = "")
	private String batchId;
}
