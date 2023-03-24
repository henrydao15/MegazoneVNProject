package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmReturnVisitData;
import com.megazone.crm.entity.VO.CrmModelFiledVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-07-06
 */
public interface ICrmReturnVisitDataService extends BaseService<CrmReturnVisitData> {
	/**
	 * @param array   array
	 * @param batchId batchId
	 */
	void saveData(List<CrmModelFiledVO> array, String batchId);

	void setDataByBatchId(CrmModel crmModel);

	void deleteByBatchId(List<String> batchList);
}
