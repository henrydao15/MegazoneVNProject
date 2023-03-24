package com.megazone.core.servlet.upload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
@Data
@ApiModel
public class FileEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty
	private String fileId;

	@ApiModelProperty
	private String fileType;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private Long size;

	@ApiModelProperty
	private String batchId;

	@ApiModelProperty
	private String url;

	@ApiModelProperty
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
	private Date createTime;

	private Long createUserId;

	@ApiModelProperty
	private String createUserName;

	@ApiModelProperty
	private String source;

	@ApiModelProperty
	private Integer readOnly;

	private String isPublic;

	@JsonIgnore
	private String path;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
		if ("1".equals(isPublic)) {
			this.url = path;
		} else {
			this.url = "/adminFile/down/" + fileId;
		}
	}
}
