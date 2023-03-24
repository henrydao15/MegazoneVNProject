package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmEmployeeContactsData;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmEmployeeContactsDataService extends BaseService<HrmEmployeeContactsData> {

	/**
	 * @param fieldId
	 * @param value
	 * @param id
	 * @return
	 */
	Integer verifyUnique(Integer fieldId, String value, Integer id);
}
