package com.megazone.crm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmReturnVisit;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper
 * </p>
 *
 * @author
 * @since 2020-07-06
 */
public interface CrmReturnVisitMapper extends BaseMapper<CrmReturnVisit> {
	/**
	 * @param id id
	 * @return data
	 */
	public CrmModel queryById(@Param("id") Integer id);
}
