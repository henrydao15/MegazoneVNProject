package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmSalaryGroup;
import com.megazone.hrm.entity.VO.SalaryGroupPageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface HrmSalaryGroupMapper extends BaseMapper<HrmSalaryGroup> {

	BasePage<SalaryGroupPageListVO> querySalaryGroupPageList(BasePage<SalaryGroupPageListVO> parse);

	/**
	 * @param employeeId
	 * @return
	 */
	HrmSalaryGroup queryEmployeeSalaryGroupByEmpId(@Param("employeeId") Integer employeeId);

	/**
	 * @param deptIds
	 * @return
	 */
	HrmSalaryGroup queryEmployeeSalaryGroupByDId(@Param("deptIds") Set<Integer> deptIds);

	List<HrmSalaryGroup> querySalaryGroupByTaxType(int taxType);
}
