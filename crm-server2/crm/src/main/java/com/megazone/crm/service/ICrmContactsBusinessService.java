package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmContactsBusiness;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface ICrmContactsBusinessService extends BaseService<CrmContactsBusiness> {
	/**
	 * @param business   ID
	 * @param contactsId ID
	 */
	public void save(Integer business, Integer contactsId);

	/**
	 * @param contactsId ID
	 */
	public void removeByContactsId(Integer contactsId);

	/**
	 * @param businessId ID
	 */
	public void removeByBusinessId(Integer... businessId);
}
