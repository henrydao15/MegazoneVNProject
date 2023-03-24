package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
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
public interface HrmInsuranceMonthEmpProjectRecordMapper extends BaseMapper<HrmInsuranceMonthEmpProjectRecord> {

	Map<String, Object> queryProjectCount(Integer iEmpRecordId);
}
