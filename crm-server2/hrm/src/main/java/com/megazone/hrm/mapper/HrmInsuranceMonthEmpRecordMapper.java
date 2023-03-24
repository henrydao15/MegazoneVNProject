package com.megazone.hrm.mapper;

import cn.hutool.core.date.DateTime;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QueryEmpInsuranceMonthBO;
import com.megazone.hrm.entity.BO.QueryInsuranceRecordListBO;
import com.megazone.hrm.entity.PO.HrmInsuranceMonthEmpRecord;
import com.megazone.hrm.entity.VO.QueryEmpInsuranceMonthVO;
import com.megazone.hrm.entity.VO.QueryInsurancePageListVO;
import com.megazone.hrm.entity.VO.SimpleHrmEmployeeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface HrmInsuranceMonthEmpRecordMapper extends BaseMapper<HrmInsuranceMonthEmpRecord> {

	BasePage<QueryEmpInsuranceMonthVO> queryEmpInsuranceMonth(BasePage<QueryEmpInsuranceMonthVO> parse, @Param("data") QueryEmpInsuranceMonthBO queryEmpInsuranceMonthBO);

	List<SimpleHrmEmployeeVO> queryNoInsuranceEmp(@Param("iRecordId") Integer iRecordId, @Param("dateTime") DateTime dateTime);

	BasePage<QueryInsurancePageListVO> myInsurancePageList(BasePage<QueryInsurancePageListVO> parse, @Param("data") QueryInsuranceRecordListBO recordListBO);

}
