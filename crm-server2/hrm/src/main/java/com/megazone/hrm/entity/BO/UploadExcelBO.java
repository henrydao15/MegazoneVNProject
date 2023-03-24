package com.megazone.hrm.entity.BO;

import com.megazone.core.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UploadExcelBO {


	@ApiModelProperty
	private String filePath;

	@ApiModelProperty
	private Long ownerUserId;

	@ApiModelProperty
	private Integer repeatHandling;

	@ApiModelProperty
	private Long messageId;

	private UserInfo userInfo;
}
