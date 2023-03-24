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
@TableName("wk_admin_file")
@ApiModel(value = "AdminFile object", description = "Attachment table")
public class AdminFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "file_id", type = IdType.ASSIGN_ID)
	private Long fileId;

	@ApiModelProperty(value = "Attachment Name")
	private String name;

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

	@ApiModelProperty(value = "file type,file,img")
	private String fileType;

	@ApiModelProperty(value = "1 local 2 Alibaba Cloud oss")
	private Integer type;

	@ApiModelProperty(value = "source 0 default 1 admin 2 crm 3 work 4 oa 5 invoicing 6 hrm")
	private Integer source;

	@ApiModelProperty(value = "1 public access 0 private access")
	private Integer isPublic;

	@ApiModelProperty(value = "batch id")
	private String batchId;


}
