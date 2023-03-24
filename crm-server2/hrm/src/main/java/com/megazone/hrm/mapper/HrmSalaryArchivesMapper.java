package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QuerySalaryArchivesListBO;
import com.megazone.hrm.entity.DTO.ExcelTemplateOption;
import com.megazone.hrm.entity.PO.HrmSalaryArchives;
import com.megazone.hrm.entity.VO.ChangeSalaryOptionVO;
import com.megazone.hrm.entity.VO.QueryChangeRecordListVO;
import com.megazone.hrm.entity.VO.QuerySalaryArchivesListVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-05
 */
public interface HrmSalaryArchivesMapper extends BaseMapper<HrmSalaryArchives> {

	BasePage<QuerySalaryArchivesListVO> querySalaryArchivesList(BasePage<QuerySalaryArchivesListVO> parse, @Param("data") QuerySalaryArchivesListBO querySalaryArchivesListBO,
																@Param("employeeIds") Collection<Integer> employeeIds);

	List<QueryChangeRecordListVO> queryChangeRecordList(@Param("employeeId") Integer employeeId);

	List<ChangeSalaryOptionVO> queryBatchChangeOption();

	List<ExcelTemplateOption> queryFixSalaryExcelExportOption();

	List<ExcelTemplateOption> queryChangeSalaryExcelExportOption();

}
