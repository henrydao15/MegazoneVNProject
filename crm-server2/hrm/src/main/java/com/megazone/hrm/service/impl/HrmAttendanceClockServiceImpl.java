package com.megazone.hrm.service.impl;

import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.common.EmployeeHolder;
import com.megazone.hrm.constant.MenuIdConstant;
import com.megazone.hrm.entity.BO.QueryAttendancePageBO;
import com.megazone.hrm.entity.BO.QueryNotesStatusBO;
import com.megazone.hrm.entity.PO.HrmAttendanceClock;
import com.megazone.hrm.entity.VO.QueryAttendancePageVO;
import com.megazone.hrm.mapper.HrmAttendanceClockMapper;
import com.megazone.hrm.service.IHrmAttendanceClockService;
import com.megazone.hrm.utils.EmployeeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class HrmAttendanceClockServiceImpl extends BaseServiceImpl<HrmAttendanceClockMapper, HrmAttendanceClock> implements IHrmAttendanceClockService {

	@Autowired
	private EmployeeUtil employeeUtil;

	@Override
	public void addOrUpdate(HrmAttendanceClock attendanceClock) {
		attendanceClock.setAttendanceTime(new Date());
		attendanceClock.setClockEmployeeId(EmployeeHolder.getEmployeeId());
		saveOrUpdate(attendanceClock);
	}

	@Override
	public BasePage<QueryAttendancePageVO> queryPageList(QueryAttendancePageBO attendancePageBO) {
		Collection<Integer> employeeIds = employeeUtil.queryDataAuthEmpIdByMenuId(MenuIdConstant.ATTENDANCE_MENU_ID);
		return getBaseMapper().queryPageList(attendancePageBO.parse(), attendancePageBO, employeeIds);
	}

	@Override
	public List<HrmAttendanceClock> queryClockListByTime(Date time, Collection<Integer> employeeIds) {
		return getBaseMapper().queryClockListByTime(time, employeeIds);
	}

	@Override
	public Set<String> queryClockStatusList(QueryNotesStatusBO queryNotesStatusBO, Collection<Integer> employeeIds) {
		return getBaseMapper().queryClockStatusList(queryNotesStatusBO, employeeIds);
	}

	@Override
	public BasePage<QueryAttendancePageVO> queryMyPageList(PageEntity pageEntity) {
		return getBaseMapper().queryMyPageList(pageEntity.parse(), EmployeeHolder.getEmployeeId());
	}
}
