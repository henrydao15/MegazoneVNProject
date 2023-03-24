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
@TableName("wk_oa_event")
@ApiModel(value = "OaEvent object", description = "Schedule")
public class OaEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "event_id", type = IdType.AUTO)
	private Integer eventId;

	@ApiModelProperty(value = "title")
	private String title;

	@ApiModelProperty(value = "Schedule Type")
	private Integer typeId;

	@ApiModelProperty(value = "start time")
	private Date startTime;

	@ApiModelProperty(value = "end time")
	private Date endTime;

	@ApiModelProperty(value = "Participant")
	private String ownerUserIds;

	@ApiModelProperty(value = "Creator ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "update time")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "Repeat Type 1 Never Repeat 2 Daily 3 Weekly 4 Monthly 5 Yearly")
	private Integer repetitionType;

	@ApiModelProperty(value = "Repeat Frequency")
	private Integer repeatRate;

	@ApiModelProperty(value = "3:week/4:month")
	private String repeatTime;

	@ApiModelProperty(value = "End Type 1 Never 2 Repeats 3 End Date")
	private Integer endType;

	@ApiModelProperty(value = "2: times/3: time")
	private String endTypeConfig;

	@ApiModelProperty(value = "loop start time")
	private Date repeatStartTime;

	@ApiModelProperty(value = "loop end time")
	private Date repeatEndTime;

	private String batchId;


	@ApiModelProperty
	@TableField(exist = false)
	private String color;


}
