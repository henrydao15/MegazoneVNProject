package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QueryDeptListBO;
import com.megazone.hrm.entity.BO.QueryEmployeeByDeptIdBO;
import com.megazone.hrm.entity.PO.HrmDept;
import com.megazone.hrm.entity.VO.DeptEmployeeVO;
import com.megazone.hrm.entity.VO.DeptVO;
import com.megazone.hrm.entity.VO.QueryEmployeeListByDeptIdVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmDeptMapper extends BaseMapper<HrmDept> {

	DeptVO queryById(@Param("deptId") Integer deptId, @Param("employeeIds") Collection<Integer> employeeIds);

	List<DeptVO> queryList(@Param("queryDeptListBO") QueryDeptListBO queryDeptListBO);

	BasePage<QueryEmployeeListByDeptIdVO> queryEmployeeByDeptId(BasePage<QueryEmployeeListByDeptIdVO> parse, @Param("data") QueryEmployeeByDeptIdBO employeeByDeptIdBO,
																@Param("employeeIds") Collection<Integer> employeeIds);

	List<DeptEmployeeVO> queryDeptEmployeeList();

	List<DeptVO> queryDeptByEmpIds(@Param("employeeIds") Collection<Integer> employeeIds);
}
