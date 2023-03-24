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
@TableName("wk_oa_log_bulletin")
@ApiModel(value = "OaLogBulletin object", description = "Work log and business ID association table")
public class OaLogBulletin implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "Log ID")
	private Integer logId;

	@ApiModelProperty(value = "Association Type 1 Customer 2 Opportunity 3 Contract 4 Payment Collection 5 Follow-up Record ")
	private Integer type;

	@ApiModelProperty(value = "Type ID")
	private Integer typeId;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

}
