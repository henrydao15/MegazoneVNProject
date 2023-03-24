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
@TableName("wk_admin_official_img")
@ApiModel(value = "AdminOfficialImg object", description = "Official website image")
public class AdminOfficialImg implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "official_img_id", type = IdType.AUTO)
	private Integer officialImgId;

	@ApiModelProperty(value = "Attachment size (bytes)")
	private Long size;

	@ApiModelProperty(value = "Creator ID")
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	@ApiModelProperty(value = "creation time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty(value = "file real path")
	private String path;

	@ApiModelProperty(value = "file path")
	private String filePath;

	@ApiModelProperty(value = "1. Official website settings 2. Business card poster")
	private Integer type;

	private String name;

	@ApiModelProperty(value = "0")
	private Integer tactic;


}
