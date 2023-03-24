package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.PO.HrmSalarySlipOption;
import com.megazone.hrm.entity.PO.HrmSalarySlipRecord;
import com.megazone.hrm.entity.VO.QuerySendDetailListVO;
import com.megazone.hrm.entity.VO.QuerySendRecordListVO;
import com.megazone.hrm.entity.VO.SlipEmployeeVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
public interface IHrmSalarySlipRecordService extends BaseService<HrmSalarySlipRecord> {

	/**
	 * @param slipEmployeePageListBO
	 * @return
	 */
	BasePage<SlipEmployeeVO> querySlipEmployeePageList(QuerySlipEmployeePageListBO slipEmployeePageListBO);

	/**
	 * @param sendSalarySlipBO
	 */
	void sendSalarySlip(SendSalarySlipBO sendSalarySlipBO);

	/**
	 * @param querySendRecordListBO
	 * @return
	 */
	BasePage<QuerySendRecordListVO> querySendRecordList(QuerySendRecordListBO querySendRecordListBO);

	/**
	 * @param querySendRecordListBO
	 * @return
	 */
	BasePage<QuerySendDetailListVO> querySendDetailList(QuerySendDetailListBO querySendRecordListBO);

	/**
	 * @param setSlipRemarksBO
	 */
	void setSlipRemarks(SetSlipRemarksBO setSlipRemarksBO);

	/**
	 * @param id
	 * @return
	 */
	List<HrmSalarySlipOption> querySlipDetail(Integer id);

	/**
	 * @param id
	 */
	void deleteSendRecord(String id);
}
