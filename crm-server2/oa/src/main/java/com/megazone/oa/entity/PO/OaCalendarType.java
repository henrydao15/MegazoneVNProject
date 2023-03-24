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
@TableName("wk_oa_calendar_type")
@ApiModel(value = "OaCalendarType object", description = "Calendar type")
public class OaCalendarType implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "calendar type id")
	@TableId(value = "type_id", type = IdType.AUTO)
	private Integer typeId;

	@ApiModelProperty(value = "type name")
	private String typeName;

	@ApiModelProperty(value = "color")
	private String color;

	@ApiModelProperty(value = "1 system type 2 custom type")
	private Integer type;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	private Integer sort;

	@TableField(exist = false)
	private Boolean select;


}
