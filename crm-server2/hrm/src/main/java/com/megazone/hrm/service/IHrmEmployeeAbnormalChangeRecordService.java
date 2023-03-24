package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.constant.AbnormalChangeType;
import com.megazone.hrm.entity.PO.HrmEmployeeAbnormalChangeRecord;

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
public interface IHrmEmployeeAbnormalChangeRecordService extends BaseService<HrmEmployeeAbnormalChangeRecord> {


	/**
	 * @param employeeId         id
	 * @param abnormalChangeType
	 */
	void addAbnormalChangeRecord(Integer employeeId, AbnormalChangeType abnormalChangeType, Date changeTime);

	/**
	 * @return
	 */
	List<HrmEmployeeAbnormalChangeRecord> queryListByDate(Date startTime, Date endTime, Collection<Integer> employeeIds, Integer type);

	/**
	 * @return
	 */
	List<HrmEmployeeAbnormalChangeRecord> queryListByDate1(int year, int monthValue, Integer type, Collection<Integer> employeeIds);
}
