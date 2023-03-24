package com.megazone.crm.entity.BO;

import com.megazone.core.feign.crm.entity.BiParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ian
 * @date 2020/8/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class CallRecordBO extends BiParams {

	@ApiModelProperty(value = "()")
	private Long talkTime;

	private String talkTimeCondition;
}
