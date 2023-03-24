package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmCustomerUserStar;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-29
 */
public interface ICrmCustomerUserStarService extends BaseService<CrmCustomerUserStar> {
	/**
	 * @param customerId id
	 * @param userId     ID
	 * @return int
	 */
	public Integer isStar(Object customerId, Long userId);

	List<Integer> starList(Long userId);
}
