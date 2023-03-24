package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.QuerySalarySlipListBO;
import com.megazone.hrm.entity.PO.HrmSalarySlip;
import com.megazone.hrm.entity.VO.QuerySalarySlipListVO;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
public interface IHrmSalarySlipService extends BaseService<HrmSalarySlip> {

	/**
	 * @param querySalarySlipListBO
	 * @return
	 */
	BasePage<QuerySalarySlipListVO> querySalarySlipList(QuerySalarySlipListBO querySalarySlipListBO);
}
