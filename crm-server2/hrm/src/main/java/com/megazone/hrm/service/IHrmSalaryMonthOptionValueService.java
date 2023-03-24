package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.DTO.ComputeSalaryDTO;
import com.megazone.hrm.entity.PO.HrmSalaryMonthOptionValue;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface IHrmSalaryMonthOptionValueService extends BaseService<HrmSalaryMonthOptionValue> {

	/**
	 * @param sEmpRecordId
	 */
	List<ComputeSalaryDTO> querySalaryOptionValue(Integer sEmpRecordId);
}
