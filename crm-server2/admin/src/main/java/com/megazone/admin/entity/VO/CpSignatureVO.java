package com.megazone.admin.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CpSignatureVO {

	@ApiModelProperty
	private String noncestr;

	@ApiModelProperty
	private Long timestamp;

	@ApiModelProperty
	private String signature;

	@ApiModelProperty
	private String corpid;

	private Long agentId;

}
