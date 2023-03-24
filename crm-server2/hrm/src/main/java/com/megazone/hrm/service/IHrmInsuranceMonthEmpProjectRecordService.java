package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmInsuranceMonthEmpProjectRecord;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface IHrmInsuranceMonthEmpProjectRecordService extends BaseService<HrmInsuranceMonthEmpProjectRecord> {

	/**
	 * @param iEmpRecordId
	 * @return
	 */
	Map<String, Object> queryProjectCount(Integer iEmpRecordId);
}
