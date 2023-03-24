package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.entity.DTO.ComputeSalaryDTO;
import com.megazone.hrm.entity.PO.HrmSalaryMonthOptionValue;
import com.megazone.hrm.mapper.HrmSalaryMonthOptionValueMapper;
import com.megazone.hrm.service.IHrmSalaryMonthOptionValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@Service
public class HrmSalaryMonthOptionValueServiceImpl extends BaseServiceImpl<HrmSalaryMonthOptionValueMapper, HrmSalaryMonthOptionValue> implements IHrmSalaryMonthOptionValueService {

	@Autowired
	private HrmSalaryMonthOptionValueMapper optionValueMapper;

	@Override
	public List<ComputeSalaryDTO> querySalaryOptionValue(Integer sEmpRecordId) {
		return optionValueMapper.querySalaryOptionValue(sEmpRecordId);
	}
}
