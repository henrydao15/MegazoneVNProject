package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
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
public interface HrmSalaryMonthOptionValueMapper extends BaseMapper<HrmSalaryMonthOptionValue> {

	List<ComputeSalaryDTO> querySalaryOptionValue(Integer sEmpRecordId);
}
