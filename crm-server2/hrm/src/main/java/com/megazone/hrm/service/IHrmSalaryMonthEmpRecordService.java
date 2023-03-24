package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmSalaryMonthEmpRecord;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface IHrmSalaryMonthEmpRecordService extends BaseService<HrmSalaryMonthEmpRecord> {


	/**
	 * @param sRecordId
	 * @param dataAuthEmployeeIds
	 * @return
	 */
	List<Integer> queryEmployeeIds(Integer sRecordId, Collection<Integer> dataAuthEmployeeIds);
}
