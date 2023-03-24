package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmEmployeeAbnormalChangeRecord;
import org.apache.ibatis.annotations.Param;

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
public interface HrmEmployeeAbnormalChangeRecordMapper extends BaseMapper<HrmEmployeeAbnormalChangeRecord> {

	List<HrmEmployeeAbnormalChangeRecord> queryListByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("employeeIds") Collection<Integer> employeeIds, @Param("type") Integer type);

	List<HrmEmployeeAbnormalChangeRecord> queryListByDate1(@Param("year") int year, @Param("month") int monthValue, @Param("type") Integer type, @Param("employeeIds") Collection<Integer> employeeIds);
}
