package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.constant.AbnormalChangeType;
import com.megazone.hrm.entity.PO.HrmEmployeeAbnormalChangeRecord;
import com.megazone.hrm.mapper.HrmEmployeeAbnormalChangeRecordMapper;
import com.megazone.hrm.service.IHrmEmployeeAbnormalChangeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-06-08
 */
@Service
public class HrmEmployeeAbnormalChangeRecordServiceImpl extends BaseServiceImpl<HrmEmployeeAbnormalChangeRecordMapper, HrmEmployeeAbnormalChangeRecord> implements IHrmEmployeeAbnormalChangeRecordService {


	@Autowired
	private HrmEmployeeAbnormalChangeRecordMapper abnormalChangeRecordMapper;

	@Override
	public void addAbnormalChangeRecord(Integer employeeId, AbnormalChangeType abnormalChangeType, Date changeTime) {
		if (abnormalChangeType.equals(AbnormalChangeType.RESIGNATION)) {

			lambdaUpdate().eq(HrmEmployeeAbnormalChangeRecord::getEmployeeId, employeeId)
					.eq(HrmEmployeeAbnormalChangeRecord::getType, AbnormalChangeType.NEW_ENTRY.getValue()).remove();
		} else if (abnormalChangeType.equals(AbnormalChangeType.NEW_ENTRY)) {

			lambdaUpdate().eq(HrmEmployeeAbnormalChangeRecord::getEmployeeId, employeeId)
					.eq(HrmEmployeeAbnormalChangeRecord::getType, AbnormalChangeType.RESIGNATION.getValue()).remove();
		}
		HrmEmployeeAbnormalChangeRecord abnormalChangeRecord = new HrmEmployeeAbnormalChangeRecord();
		abnormalChangeRecord.setType(abnormalChangeType.getValue());
		abnormalChangeRecord.setEmployeeId(employeeId);
		abnormalChangeRecord.setChangeTime(changeTime);
		save(abnormalChangeRecord);
	}

	@Override
	public List<HrmEmployeeAbnormalChangeRecord> queryListByDate(Date startTime, Date endTime, Collection<Integer> employeeIds, Integer type) {
		return abnormalChangeRecordMapper.queryListByDate(startTime, endTime, employeeIds, type);
	}

	@Override
	public List<HrmEmployeeAbnormalChangeRecord> queryListByDate1(int year, int monthValue, Integer type, Collection<Integer> employeeIds) {
		return abnormalChangeRecordMapper.queryListByDate1(year, monthValue, type, employeeIds);
	}
}
