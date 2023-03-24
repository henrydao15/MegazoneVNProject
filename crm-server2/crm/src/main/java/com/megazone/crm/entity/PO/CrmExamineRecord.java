package com.megazone.crm.entity.PO;

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
@TableName("wk_crm_examine_record")
@ApiModel(value = "CrmExamineRecord", description = "")
public class CrmExamineRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	@TableId(value = "record_id", type = IdType.AUTO)
	private Integer recordId;

	@ApiModelProperty(value = "ID")
	private Integer examineId;

	@ApiModelProperty(value = "ID")
	private Long examineStepId;

	@ApiModelProperty(value = " 0  1  2  3  4 ")
	private Integer examineStatus;

	@ApiModelProperty(value = "")
	private Long createUser;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	private String remarks;


}
