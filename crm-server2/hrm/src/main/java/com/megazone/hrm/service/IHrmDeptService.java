package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.AddDeptBO;
import com.megazone.hrm.entity.BO.QueryDeptListBO;
import com.megazone.hrm.entity.BO.QueryEmployeeByDeptIdBO;
import com.megazone.hrm.entity.PO.HrmDept;
import com.megazone.hrm.entity.VO.DeptEmployeeVO;
import com.megazone.hrm.entity.VO.DeptVO;
import com.megazone.hrm.entity.VO.QueryEmployeeListByDeptIdVO;
import com.megazone.hrm.entity.VO.SimpleHrmDeptVO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmDeptService extends BaseService<HrmDept> {

	/**
	 * @param addDeptBO
	 */
	void addOrUpdate(AddDeptBO addDeptBO);

	/**
	 * @param deptId
	 * @return
	 */
	DeptVO queryById(Integer deptId);

	/**
	 * @param queryDeptListBO
	 * @return
	 */
	List<DeptVO> queryTreeList(QueryDeptListBO queryDeptListBO);

	/**
	 * @param deptIds
	 * @return
	 */
	List<SimpleHrmDeptVO> querySimpleDeptList(Collection<Integer> deptIds);

	/**
	 * @param deptId
	 * @return
	 */
	SimpleHrmDeptVO querySimpleDept(Integer deptId);

	/**
	 * @param employeeByDeptIdBO
	 * @return
	 */
	BasePage<QueryEmployeeListByDeptIdVO> queryEmployeeByDeptId(QueryEmployeeByDeptIdBO employeeByDeptIdBO);

	/**
	 * @param deptId
	 */
	void deleteDeptById(String deptId);

	/**
	 * @param deptIds
	 * @return
	 */
	Set<Integer> queryChildDeptId(Collection<Integer> deptIds);

	/**
	 * @param deptId
	 * @return
	 */
	Set<Integer> queryParentDeptId(Integer deptId);

	/**
	 * @return
	 */
	List<DeptEmployeeVO> queryDeptEmployeeList();

}
