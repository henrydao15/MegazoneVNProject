package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmContactsData;
import com.megazone.crm.entity.VO.CrmModelFiledVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface ICrmContactsDataService extends BaseService<CrmContactsData> {
	/**
	 * @param crmModel crmModel
	 */
	public void setDataByBatchId(CrmModel crmModel);

	/**
	 * @param array   data
	 * @param batchId batchId
	 */
	public void saveData(List<CrmModelFiledVO> array, String batchId);

	/**
	 * @param batchId data
	 */
	public void deleteByBatchId(List<String> batchId);
}
