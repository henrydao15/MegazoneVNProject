package com.megazone.admin.entity.PO;

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
@TableName("wk_admin_language_pack")
@ApiModel(value = "AdminLanguagePack Object", description = "Language Pack Table")
public class AdminLanguagePack implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "language pack id")
	@TableId(value = "language_pack_id", type = IdType.AUTO)
	private Integer languagePackId;

	@ApiModelProperty(value = "language pack name")
	private String languagePackName;

	@ApiModelProperty(value = "Language pack content")
	private String languagePackContext;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "Creator ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "update time")
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;
}
