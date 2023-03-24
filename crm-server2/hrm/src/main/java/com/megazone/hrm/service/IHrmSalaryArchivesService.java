package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.DTO.ExcelTemplateOption;
import com.megazone.hrm.entity.PO.HrmSalaryArchives;
import com.megazone.hrm.entity.PO.HrmSalaryArchivesOption;
import com.megazone.hrm.entity.VO.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-05
 */
public interface IHrmSalaryArchivesService extends BaseService<HrmSalaryArchives> {

	/**
	 * @param querySalaryArchivesListBO
	 * @return
	 */
	BasePage<QuerySalaryArchivesListVO> querySalaryArchivesList(QuerySalaryArchivesListBO querySalaryArchivesListBO);

	/**
	 * @param setFixSalaryRecordBO
	 */
	void setFixSalaryRecord(SetFixSalaryRecordBO setFixSalaryRecordBO);

	/**
	 * @param employeeId
	 * @return
	 */
	QuerySalaryArchivesByIdVO querySalaryArchivesById(Integer employeeId);

	/**
	 * @param employeeId
	 * @return
	 */
	List<QueryChangeRecordListVO> queryChangeRecordList(Integer employeeId);

	/**
	 * @param id
	 * @return
	 */
	FixSalaryRecordDetailVO queryFixSalaryRecordById(Integer id);

	/**
	 * @param setChangeSalaryRecordBO
	 */
	void setChangeSalaryRecord(SetChangeSalaryRecordBO setChangeSalaryRecordBO);

	/**
	 * @param id
	 * @return
	 */
	ChangeSalaryRecordDetailVO queryChangeSalaryRecordById(Integer id);

	/**
	 * @param id
	 */
	void cancelChangeSalary(Integer id);

	/**
	 * @param id
	 */
	void deleteChangeSalary(Integer id);

	/**
	 * @param changeOptionValueBO
	 * @return
	 */
	QueryChangeOptionValueVO queryChangeOptionValue(QueryChangeOptionValueBO changeOptionValueBO);

	/**
	 * @return
	 */
	List<ChangeSalaryOptionVO> queryBatchChangeOption();

	/**
	 * @param batchChangeSalaryRecordBO
	 * @return
	 */
	Map<String, Integer> batchChangeSalaryRecord(BatchChangeSalaryRecordBO batchChangeSalaryRecordBO);


	/**
	 * @return
	 */
	List<ExcelTemplateOption> queryFixSalaryExcelExportOption();


	/**
	 * @return
	 */
	List<ExcelTemplateOption> queryChangeSalaryExcelExportOption();

	/**
	 * @param multipartFile
	 * @return
	 */
	Long exportFixSalaryRecord(MultipartFile multipartFile);

	/**
	 * @param multipartFile
	 * @return
	 */
	Long exportChangeSalaryRecord(MultipartFile multipartFile);

	/**
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	List<HrmSalaryArchivesOption> querySalaryArchivesOption(Integer employeeId, int year, int month);
}
