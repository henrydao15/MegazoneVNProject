package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QueryInsurancePageListBO;
import com.megazone.hrm.entity.BO.QueryInsuranceRecordListBO;
import com.megazone.hrm.entity.PO.HrmInsuranceMonthRecord;
import com.megazone.hrm.entity.VO.QueryInsurancePageListVO;
import com.megazone.hrm.entity.VO.QueryInsuranceRecordListVO;
import org.apache.ibatis.annotations.Param;

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
public interface HrmInsuranceMonthRecordMapper extends BaseMapper<HrmInsuranceMonthRecord> {

	/**
	 * @return
	 */
	List<Map<String, Integer>> queryInsuranceEmployee();

	BasePage<QueryInsuranceRecordListVO> queryInsuranceRecordList(BasePage<QueryInsuranceRecordListVO> parse,
																  @Param("data") QueryInsuranceRecordListBO recordListBO,
																  @Param("employeeIds") Collection<Integer> employeeIds, @Param("isAll") boolean isAll);

	BasePage<QueryInsurancePageListVO> queryInsurancePageList(BasePage<QueryInsurancePageListVO> parse, @Param("data") QueryInsurancePageListBO queryInsurancePageListBO,
															  @Param("employeeIds") Collection<Integer> employeeIds);

	QueryInsuranceRecordListVO queryInsuranceRecord(@Param("iRecordId") String iRecordId, @Param("employeeIds") Collection<Integer> employeeIds);

	List<Integer> queryDeleteEmpRecordIds(Integer iRecordId);
}
