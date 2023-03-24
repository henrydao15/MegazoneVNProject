package com.megazone.crm.mapper;

import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmLeads;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-21
 */
public interface CrmLeadsMapper extends BaseMapper<CrmLeads> {
	/**
	 * @param id     id
	 * @param userId ID
	 * @return data
	 */
	public CrmModel queryById(@Param("id") Integer id, @Param("userId") Long userId);

	List<String> eventLeads(@Param("data") CrmEventBO crmEventBO);

	List<Integer> eventLeadsList(@Param("userId") Long userId, @Param("time") Date time);
}
