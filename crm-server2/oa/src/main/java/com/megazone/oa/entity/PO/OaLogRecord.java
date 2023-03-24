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
@TableName("wk_oa_log_record")
@ApiModel(value = "OaLogRecord object", description = "Log related sales report")
public class OaLogRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private Integer logId;

	@ApiModelProperty(value = "Number of customers")
	private Integer customerNum;

	@ApiModelProperty(value = "Number of Opportunities")
	private Integer businessNum;

	@ApiModelProperty(value = "Number of contracts")
	private Integer contractNum;

	@ApiModelProperty(value = "Return amount")
	private BigDecimal receivablesMoney;

	@ApiModelProperty(value = "Follow-up record")
	private Integer activityNum;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;


}
