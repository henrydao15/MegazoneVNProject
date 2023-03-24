package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.QueryAttendancePageBO;
import com.megazone.hrm.entity.BO.QueryNotesStatusBO;
import com.megazone.hrm.entity.PO.HrmAttendanceClock;
import com.megazone.hrm.entity.VO.QueryAttendancePageVO;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-12-07
 */
public interface IHrmAttendanceClockService extends BaseService<HrmAttendanceClock> {

	/**
	 * @param attendanceClock
	 */
	void addOrUpdate(HrmAttendanceClock attendanceClock);

	/**
	 * @param attendancePageBO
	 * @return
	 */
	BasePage<QueryAttendancePageVO> queryPageList(QueryAttendancePageBO attendancePageBO);

	/**
	 * @param time
	 * @param employeeIds
	 * @return
	 */
	List<HrmAttendanceClock> queryClockListByTime(Date time, Collection<Integer> employeeIds);

	/**
	 * @param queryNotesStatusBO
	 * @param employeeIds
	 * @return
	 */
	Set<String> queryClockStatusList(QueryNotesStatusBO queryNotesStatusBO, Collection<Integer> employeeIds);

	/**
	 * @param pageEntity
	 * @return
	 */
	BasePage<QueryAttendancePageVO> queryMyPageList(PageEntity pageEntity);
}
