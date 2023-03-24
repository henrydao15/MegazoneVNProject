package com.megazone.crm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmProduct;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface CrmProductMapper extends BaseMapper<CrmProduct> {
	/**
	 * @param id     id
	 * @param userId ID
	 * @return data
	 */
	public CrmModel queryById(Integer id, Long userId);
}
