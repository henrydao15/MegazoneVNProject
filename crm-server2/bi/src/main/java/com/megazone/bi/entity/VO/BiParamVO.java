package com.megazone.bi.entity.VO;

import com.megazone.core.feign.crm.entity.BiParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BiParamVO extends BiParams {
	private String categoryId;
}
