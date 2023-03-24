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
@TableName("wk_crm_examine")
@ApiModel(value = "CrmExamine", description = "")
public class CrmExamine implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "examine_id", type = IdType.AUTO)
	private Integer examineId;

	@ApiModelProperty(value = "1  2  3")
	private Integer categoryType;

	@ApiModelProperty(value = " 1  2 ")
	private Integer examineType;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String icon;

	@ApiModelProperty(value = "ID（0）")
	private String deptIds;

	@ApiModelProperty(value = "ID")
	private String userIds;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	@ApiModelProperty(value = "")
	private Long updateUserId;

	@ApiModelProperty(value = " 1  0  2 ")
	private Integer status;

	@ApiModelProperty(value = "")
	private String remarks;


}
