package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.entity.PO.HrmInsuranceMonthEmpProjectRecord;
import com.megazone.hrm.mapper.HrmInsuranceMonthEmpProjectRecordMapper;
import com.megazone.hrm.service.IHrmInsuranceMonthEmpProjectRecordService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@Service
public class HrmInsuranceMonthEmpProjectRecordServiceImpl extends BaseServiceImpl<HrmInsuranceMonthEmpProjectRecordMapper, HrmInsuranceMonthEmpProjectRecord> implements IHrmInsuranceMonthEmpProjectRecordService {

	@Override
	public Map<String, Object> queryProjectCount(Integer iEmpRecordId) {
		return getBaseMapper().queryProjectCount(iEmpRecordId);
	}
}
