package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmLeadsUserStar;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-21
 */
public interface ICrmLeadsUserStarService extends BaseService<CrmLeadsUserStar> {
	/**
	 * @param leadsId id
	 * @param userId  ID
	 * @return int
	 */
	public Integer isStar(Object leadsId, Long userId);

	List<Integer> starList(Long userId);
}
