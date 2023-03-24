package com.megazone.crm.entity.BO;

import com.megazone.core.feign.crm.entity.BiParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author
 * @date 2020/11/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CrmSearchParamsBO extends BiParams {
	private CrmSearchBO.Search entity;
}
