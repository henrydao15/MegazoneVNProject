package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QueryAttendancePageBO;
import com.megazone.hrm.entity.BO.QueryNotesStatusBO;
import com.megazone.hrm.entity.PO.HrmAttendanceClock;
import com.megazone.hrm.entity.VO.QueryAttendancePageVO;
import org.apache.ibatis.annotations.Param;

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
public interface HrmAttendanceClockMapper extends BaseMapper<HrmAttendanceClock> {

	BasePage<QueryAttendancePageVO> queryPageList(BasePage<QueryAttendancePageVO> parse,
												  @Param("data") QueryAttendancePageBO attendancePageBO, @Param("employeeIds") Collection<Integer> employeeIds);

	List<HrmAttendanceClock> queryClockListByTime(@Param("time") Date time, @Param("employeeIds") Collection<Integer> employeeIds);

	Set<String> queryClockStatusList(@Param("data") QueryNotesStatusBO queryNotesStatusBO, @Param("employeeIds") Collection<Integer> employeeIds);

	BasePage<QueryAttendancePageVO> queryMyPageList(BasePage<Object> parse, @Param("employeeId") Integer employeeId);
}
