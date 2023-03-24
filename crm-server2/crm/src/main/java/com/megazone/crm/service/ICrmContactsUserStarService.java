package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmContactsUserStar;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface ICrmContactsUserStarService extends BaseService<CrmContactsUserStar> {
	/**
	 * @param contactsId id
	 * @param userId     ID
	 * @return int
	 */
	public Integer isStar(Object contactsId, Long userId);

	List<Integer> starList(Long userId);
}
