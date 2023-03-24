package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.SetSalaryOptionBO;
import com.megazone.hrm.entity.PO.HrmSalaryOption;
import com.megazone.hrm.entity.VO.SalaryOptionDetailVO;
import com.megazone.hrm.entity.VO.SalaryOptionVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface IHrmSalaryOptionService extends BaseService<HrmSalaryOption> {

	/**
	 * @return
	 */
	SalaryOptionDetailVO querySalaryOptionDetail();

	/**
	 * @return
	 */
	List<SalaryOptionVO> querySalaryOptionList();


	/**
	 * @param setSalaryOptionBO
	 */
	void setSalaryOption(SetSalaryOptionBO setSalaryOptionBO);

}
