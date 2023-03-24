package com.megazone.crm.entity.BO;

import com.megazone.core.entity.UserInfo;
import com.megazone.crm.constant.CrmEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UploadExcelBO {

	@ApiModelProperty
	private CrmEnum crmEnum;

	@ApiModelProperty
	private String filePath;

	@ApiModelProperty
	private Integer poolId;

	@ApiModelProperty
	private Integer repeatHandling;

	@ApiModelProperty
	private Long messageId;

	@ApiModelProperty
	private UserInfo userInfo;
}
