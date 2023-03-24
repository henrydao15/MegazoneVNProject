package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmInstrumentSort;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-06-04
 */
public interface ICrmInstrumentSortService extends BaseService<CrmInstrumentSort> {

	/**
	 * @return data
	 */
	public JSONObject queryModelSort();

	/**
	 * @param object obj
	 */
	public void setModelSort(JSONObject object);
}
