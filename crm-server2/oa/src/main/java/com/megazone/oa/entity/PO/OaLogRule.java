package com.megazone.oa.entity.PO;

import com.baomidou.mybatisplus.annotation.*;
import com.megazone.core.feign.admin.entity.SimpleUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wk_oa_log_rule")
@ApiModel(value = "OaLogRule object", description = "")
public class OaLogRule implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "rule_id", type = IdType.AUTO)
	private Integer ruleId;

	@ApiModelProperty(value = "status 0 disabled 1 enabled")
	private Integer status;

	@ApiModelProperty(value = "Employee id to submit, “,“split")
	private String memberUserId;

	@ApiModelProperty(value = "Log Type 1 Daily 2 Weekly 3 Monthly Report")
	private Integer type;

	@ApiModelProperty(value = "The dates 1-6 need to be counted every week are Monday to Saturday and 7 is Sunday")
	private String effectiveDay;

	@ApiModelProperty(value = "Start Date")
	private Integer startDay;

	@ApiModelProperty(value = "start time")
	private String startTime;

	@ApiModelProperty(value = "end date")
	private Integer endDay;

	@ApiModelProperty(value = "end time")
	private String endTime;

	@ApiModelProperty(value = "Creator")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	@TableField(exist = false)
	private List<SimpleUser> memberUser;


}
