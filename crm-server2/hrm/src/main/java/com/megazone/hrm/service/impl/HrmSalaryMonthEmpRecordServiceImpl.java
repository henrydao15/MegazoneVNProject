package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.entity.PO.HrmSalaryMonthEmpRecord;
import com.megazone.hrm.mapper.HrmSalaryMonthEmpRecordMapper;
import com.megazone.hrm.service.IHrmSalaryMonthEmpRecordService;
import org.springframework.stereotype.Service;

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
@Service
public class HrmSalaryMonthEmpRecordServiceImpl extends BaseServiceImpl<HrmSalaryMonthEmpRecordMapper, HrmSalaryMonthEmpRecord> implements IHrmSalaryMonthEmpRecordService {

	@Override
	public List<Integer> queryEmployeeIds(Integer sRecordId, Collection<Integer> dataAuthEmployeeIds) {
		return getBaseMapper().queryEmployeeIds(sRecordId, dataAuthEmployeeIds);
	}
}
