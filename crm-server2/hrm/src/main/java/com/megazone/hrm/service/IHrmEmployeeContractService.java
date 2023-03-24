package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmEmployeeContract;
import com.megazone.hrm.entity.VO.ContractInformationVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmEmployeeContractService extends BaseService<HrmEmployeeContract> {

	/**
	 * @param employeeId
	 * @return
	 */
	List<ContractInformationVO> contractInformation(Integer employeeId);

	/**
	 * @param employeeContract
	 */
	void addOrUpdateContract(HrmEmployeeContract employeeContract);

	/**
	 * @param contractId
	 */
	void deleteContract(Integer contractId);

	/**
	 * @return
	 */
	List<Integer> queryToExpireContractCount();

}
