package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.constant.TaxType;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.PO.HrmSalaryMonthRecord;
import com.megazone.hrm.entity.VO.QueryHistorySalaryDetailVO;
import com.megazone.hrm.entity.VO.QueryHistorySalaryListVO;
import com.megazone.hrm.entity.VO.QuerySalaryPageListVO;
import com.megazone.hrm.entity.VO.SalaryOptionHeadVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface IHrmSalaryMonthRecordService extends BaseService<HrmSalaryMonthRecord> {
	/**
	 * @return
	 */
	List<SalaryOptionHeadVO> querySalaryOptionHead();

	/**
	 * @param type
	 * @param taxType
	 * @return
	 */
	List<Map<String, Object>> queryPaySalaryEmployeeListByType(Integer type, TaxType taxType);

	/**
	 * @param sRecordId
	 * @param isSyncInsuranceData
	 * @param attendanceFile
	 * @param additionalDeductionFile
	 * @param cumulativeTaxOfLastMonthFile
	 */
	void computeSalaryData(Integer sRecordId, Boolean isSyncInsuranceData, MultipartFile attendanceFile, MultipartFile additionalDeductionFile, MultipartFile cumulativeTaxOfLastMonthFile);

	/**
	 * @param querySalaryPageListBO
	 * @return
	 */
	BasePage<QuerySalaryPageListVO> querySalaryPageList(QuerySalaryPageListBO querySalaryPageListBO);

	/**
	 * @param updateSalaryBOList
	 */
	void updateSalary(List<UpdateSalaryBO> updateSalaryBOList);

	/**
	 *
	 */
	HrmSalaryMonthRecord computeSalaryCount(HrmSalaryMonthRecord lastSalaryMonthRecord);

	/**
	 * @param queryHistorySalaryListBO
	 * @return
	 */
	BasePage<QueryHistorySalaryListVO> queryHistorySalaryList(QueryHistorySalaryListBO queryHistorySalaryListBO);

	/**
	 * @param queryHistorySalaryDetailBO
	 * @return
	 */
	QueryHistorySalaryDetailVO queryHistorySalaryDetail(QueryHistorySalaryDetailBO queryHistorySalaryDetailBO);

	/**
	 * @param submitExamineBO
	 */
	void submitExamine(SubmitExamineBO submitExamineBO);

	/**
	 *
	 */
	void addNextMonthSalary();


	/**
	 * @return
	 */
	Map<Integer, Long> queryEmployeeChangeNum();

	/**
	 * @return
	 */
	HrmSalaryMonthRecord queryLastSalaryMonthRecord();

	/**
	 * @param sRecordId
	 * @param checkStatus
	 */
	void updateCheckStatus(Integer sRecordId, Integer checkStatus);

	/**
	 * @return
	 */
	List<Map<String, Object>> queryNoPaySalaryEmployee();

	/**
	 * @param sRecordId
	 * @return
	 */
	List<Map<String, Object>> querySalaryOptionCount(String sRecordId);

	/**
	 * @param sRecordId
	 */
	void deleteSalary(Integer sRecordId);


}
