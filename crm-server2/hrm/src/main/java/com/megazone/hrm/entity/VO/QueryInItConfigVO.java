package com.megazone.hrm.entity.VO;

import com.megazone.hrm.entity.PO.HrmSalaryConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class QueryInItConfigVO {

	@ApiModelProperty
	private HrmSalaryConfig otherInitConfig;

	@ApiModelProperty
	private Map<Integer, Integer> statusInitConfig;
}
