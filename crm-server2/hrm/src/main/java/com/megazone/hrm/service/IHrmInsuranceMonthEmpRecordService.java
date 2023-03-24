package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.AddInsuranceEmpBO;
import com.megazone.hrm.entity.BO.QueryEmpInsuranceMonthBO;
import com.megazone.hrm.entity.BO.QueryInsuranceRecordListBO;
import com.megazone.hrm.entity.BO.UpdateInsuranceProjectBO;
import com.megazone.hrm.entity.PO.HrmInsuranceMonthEmpRecord;
import com.megazone.hrm.entity.VO.EmpInsuranceByIdVO;
import com.megazone.hrm.entity.VO.QueryEmpInsuranceMonthVO;
import com.megazone.hrm.entity.VO.SimpleHrmEmployeeVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface IHrmInsuranceMonthEmpRecordService extends BaseService<HrmInsuranceMonthEmpRecord> {

	/**
	 * @param queryEmpInsuranceMonthBO
	 * @return
	 */
	BasePage<QueryEmpInsuranceMonthVO> queryEmpInsuranceMonth(QueryEmpInsuranceMonthBO queryEmpInsuranceMonthBO);

	/**
	 * @param iempRecordId
	 * @return
	 */
	EmpInsuranceByIdVO queryById(String iempRecordId);

	/**
	 * @param ids
	 */
	void stop(List<Integer> ids);

	/**
	 * @param updateInsuranceProjectBO
	 */
	void updateInsuranceProject(UpdateInsuranceProjectBO updateInsuranceProjectBO);

	/**
	 * @param addInsuranceEmpBO
	 */
	void addInsuranceEmp(AddInsuranceEmpBO addInsuranceEmpBO);

	/**
	 * @param iRecordId
	 * @return
	 */
	List<SimpleHrmEmployeeVO> queryNoInsuranceEmp(Integer iRecordId);

	/**
	 * @param updateInsuranceProjectBO
	 */
	void batchUpdateInsuranceProject(UpdateInsuranceProjectBO updateInsuranceProjectBO);

	/**
	 * @param recordListBO
	 * @return
	 */
	BasePage<HrmInsuranceMonthEmpRecord> myInsurancePageList(QueryInsuranceRecordListBO recordListBO);
}
