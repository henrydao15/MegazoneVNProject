package com.megazone.admin.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@ApiModel
public class AdminLanguagePackVO implements Serializable {


	@ApiModelProperty(value = "language pack id")
	private Integer languagePackId;

	@ApiModelProperty(value = "language pack name")
	private String languagePackName;

	@ApiModelProperty(value = "Creator ID")
	private Long createUserId;

	@ApiModelProperty(value = "creation time")
	private Date createTime;

	@ApiModelProperty(value = "update time")
	private Date updateTime;

	@ApiModelProperty(value = "Default language, 0, no, 1, yes")
	private Integer defaultLanguage = 0;
}
