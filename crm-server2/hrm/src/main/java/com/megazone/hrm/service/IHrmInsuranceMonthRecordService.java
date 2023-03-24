package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.QueryInsurancePageListBO;
import com.megazone.hrm.entity.BO.QueryInsuranceRecordListBO;
import com.megazone.hrm.entity.PO.HrmInsuranceMonthRecord;
import com.megazone.hrm.entity.VO.QueryInsurancePageListVO;
import com.megazone.hrm.entity.VO.QueryInsuranceRecordListVO;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface IHrmInsuranceMonthRecordService extends BaseService<HrmInsuranceMonthRecord> {


	/**
	 * @return
	 */
	Integer computeInsuranceData();

	/**
	 * @param recordListBO
	 * @return
	 */
	BasePage<QueryInsuranceRecordListVO> queryInsuranceRecordList(QueryInsuranceRecordListBO recordListBO);

	/**
	 * @param queryInsurancePageListBO
	 * @return
	 */
	BasePage<QueryInsurancePageListVO> queryInsurancePageList(QueryInsurancePageListBO queryInsurancePageListBO);

	/**
	 * @param iRecordId
	 * @return
	 */
	QueryInsuranceRecordListVO queryInsuranceRecord(String iRecordId);

	/**
	 * @param iRecordId
	 */
	void deleteInsurance(Integer iRecordId);
}
