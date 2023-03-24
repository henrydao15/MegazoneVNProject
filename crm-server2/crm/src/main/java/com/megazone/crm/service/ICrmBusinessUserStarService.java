package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmBusinessUserStar;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
public interface ICrmBusinessUserStarService extends BaseService<CrmBusinessUserStar> {
	/**
	 * @param businessId id
	 * @param userId     ID
	 * @return int
	 */
	public Integer isStar(Object businessId, Long userId);

	List<Integer> starList(Long userId);
}
