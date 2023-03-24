package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmEmployeeContacts;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmEmployeeContactsService extends BaseService<HrmEmployeeContacts> {

	/**
	 * @param fieldName
	 * @param value
	 * @param contactsId
	 * @return
	 */
	Integer verifyUnique(String fieldName, String value, Integer contactsId);
}
