package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QueryHistorySalaryDetailBO;
import com.megazone.hrm.entity.BO.QuerySalaryPageListBO;
import com.megazone.hrm.entity.PO.HrmSalaryMonthEmpRecord;
import com.megazone.hrm.entity.VO.QuerySalaryListVO;
import com.megazone.hrm.entity.VO.QuerySalaryPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface HrmSalaryMonthEmpRecordMapper extends BaseMapper<HrmSalaryMonthEmpRecord> {

	/**
	 * @return
	 */
	List<Map<String, Object>> queryPaySalaryEmployeeList(@Param("endTime") Date endTime, @Param("employeeIds") Collection<Integer> dataAuthEmployeeIds);

	BasePage<QuerySalaryPageListVO> querySalaryPageList(BasePage<QuerySalaryPageListVO> parse, @Param("data") QuerySalaryPageListBO querySalaryPageListBO,
														@Param("employeeIds") Collection<Integer> dataAuthEmployeeIds);

	BasePage<QuerySalaryPageListVO> querySalaryPageListByRecordId(BasePage<QuerySalaryPageListVO> parse, @Param("data") QueryHistorySalaryDetailBO queryHistorySalaryDetailBO,
																  @Param("employeeIds") Collection<Integer> employeeIds);

	List<Integer> querysEmpRecordIds(@Param("data") QuerySalaryPageListBO querySalaryPageListBO,
									 @Param("employeeIds") Collection<Integer> dataAuthEmployeeIds);

	BasePage<QuerySalaryListVO> querySalaryRecord(BasePage<Object> parse, @Param("employeeId") Integer employeeId);

	List<Integer> queryEmployeeIds(@Param("sRecordId") Integer sRecordId, @Param("employeeIds") Collection<Integer> dataAuthEmployeeIds);
}
