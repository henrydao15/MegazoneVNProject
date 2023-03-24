package com.megazone.crm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.PO.CrmBusinessProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
public interface CrmBusinessProductMapper extends BaseMapper<CrmBusinessProduct> {
	/**
	 * @param businessId ID
	 * @return data
	 */
	public List<CrmBusinessProduct> queryList(@Param("businessId") Integer businessId);
}
