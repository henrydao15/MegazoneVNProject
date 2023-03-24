package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QueryHistorySalaryListBO;
import com.megazone.hrm.entity.PO.HrmSalaryMonthRecord;
import com.megazone.hrm.entity.VO.QueryHistorySalaryDetailVO;
import com.megazone.hrm.entity.VO.QueryHistorySalaryListVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Collection;
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
public interface HrmSalaryMonthRecordMapper extends BaseMapper<HrmSalaryMonthRecord> {

	BigDecimal queryBeforeTaxTotalSalary(@Param("employeeId") Integer employeeId, @Param("year") Integer year, @Param("month") Integer month);

	/**
	 * @param sRecordId
	 * @return
	 */
	Map<String, Object> queryMonthSalaryCount(Integer sRecordId);

	BasePage<QueryHistorySalaryListVO> queryHistorySalaryList(BasePage<Object> parse, @Param("data") QueryHistorySalaryListBO queryHistorySalaryListBO,
															  @Param("employeeIds") Collection<Integer> employeeIds);

	List<Map<String, Object>> querySalaryOptionCount(String sRecordId);

	List<Map<String, Object>> querySalaryByIds(@Param("sEmpRecordIds") List<Integer> sEmpRecordIds, @Param("sRecordId") Integer sRecordId);

	List<Integer> queryDeleteEmpRecordIds(Integer sRecordId);

	QueryHistorySalaryDetailVO queryHistorySalaryDetail(@Param("sRecordId") Integer sRecordId, @Param("employeeIds") Collection<Integer> employeeIds);

}
