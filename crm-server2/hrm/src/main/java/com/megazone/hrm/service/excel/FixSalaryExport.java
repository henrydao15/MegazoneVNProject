package com.megazone.hrm.service.excel;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.megazone.core.common.cache.AdminCacheKey;
import com.megazone.core.exception.CrmException;
import com.megazone.hrm.entity.BO.SetFixSalaryRecordBO;
import com.megazone.hrm.entity.BO.UploadExcelBO;
import com.megazone.hrm.entity.PO.HrmEmployee;
import com.megazone.hrm.entity.VO.ChangeSalaryOptionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class FixSalaryExport extends ExcelImport {


	public FixSalaryExport(UploadExcelBO uploadExcelBO, MultipartFile file) {
		uploadExcelBO.setFilePath(getFilePath(file));
		super.uploadExcelBO = uploadExcelBO;
	}

	@Override
	public void importExcel() {
		List<ChangeSalaryOptionVO> salaryOptions = archivesService.queryChangeSalaryExcelExportOption().stream().map(option -> {
			ChangeSalaryOptionVO changeSalaryOptionVO = new ChangeSalaryOptionVO();
			changeSalaryOptionVO.setName(option.getName());
			changeSalaryOptionVO.setCode(option.getCode());
			return changeSalaryOptionVO;
		}).collect(Collectors.toList());
		List<ChangeSalaryOptionVO> proSalaryOptions = salaryOptions.stream().filter(option -> option.getCode() >= 10100 && option.getCode() <= 10103).map(option -> {
			ChangeSalaryOptionVO changeSalaryOptionVO = new ChangeSalaryOptionVO();
			changeSalaryOptionVO.setName(option.getName());
			changeSalaryOptionVO.setCode(option.getCode());
			return changeSalaryOptionVO;
		}).collect(Collectors.toList());
		ExcelUtil.readBySax(uploadExcelBO.getFilePath(), 0, (int sheetIndex, int rowIndex, List<Object> rowList) -> {
			try {
				if (this.num.incrementAndGet() > 10001) {
					rowList.add(0, "Up to 10000 pieces of data can be imported at the same time");
					errorList.add(rowList);
					return;
				}
				redis.setex(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + getUploadExcelBO().getMessageId().toString(), UPLOAD_EXCEL_EXIST_TIME, Math.max(num.get() - 2, 0));
				if (rowIndex == 6) {
					rowList.add(0, "Error Reason");
					errorList.add(rowList);
				}
				if (rowIndex < 7) {
					return;
				}
				SetFixSalaryRecordBO setFixSalaryRecordBO = new SetFixSalaryRecordBO();
				String jobNumber = Optional.ofNullable(rowList.get(1)).map(String::valueOf).orElse("");
				if (StrUtil.isEmpty(jobNumber)) {
					rowList.add(0, "Please fill in the job number");
					errorList.add(rowList);
					return;
				} else {
					Optional<HrmEmployee> employeeOpt = employeeService.lambdaQuery().select(HrmEmployee::getEmployeeId).eq(HrmEmployee::getIsDel, 0).eq(HrmEmployee::getJobNumber, jobNumber).last("limit 1").oneOpt();
					if (employeeOpt.isPresent()) {
						setFixSalaryRecordBO.setEmployeeId(employeeOpt.get().getEmployeeId());
					} else {
						rowList.add(0, "The employee corresponding to the job number does not exist");
						errorList.add(rowList);
						return;
					}
				}
				BigDecimal proSum = new BigDecimal(0);
				BigDecimal sum = new BigDecimal(0);
				for (int i = 0; i < proSalaryOptions.size(); i++) {
					ChangeSalaryOptionVO changeSalaryOption = proSalaryOptions.get(i);
					int index = i + 4;
					if (rowList.size() > index) {
						String value = getValue(rowList.get(index));
						String[] split = value.split(",");
						if (split[0].length() > 7 || (split.length == 2 && split[1].length() > 2)) {
							rowList.add(0, "Incorrect salary data format");
							errorList.add(rowList);
							return;
						}
						changeSalaryOption.setValue(value);
						proSum = proSum.add(new BigDecimal(value));
					} else {
						changeSalaryOption.setValue("0");
						proSum = proSum.add(new BigDecimal(0));
					}
				}
				for (int i = 0; i < salaryOptions.size(); i++) {
					ChangeSalaryOptionVO changeSalaryOption = salaryOptions.get(i);
					int index = i + 7;
					if (rowList.size() > index) {
						String value = getValue(rowList.get(index));
						String[] split = value.split(",");
						if (split[0].length() > 7 || (split.length == 2 && split[1].length() > 2)) {
							rowList.add(0, "Incorrect salary data format");
							errorList.add(rowList);
							return;
						}
						changeSalaryOption.setValue(value);
						sum = sum.add(new BigDecimal(value));
					} else {
						changeSalaryOption.setValue("0");
						sum = sum.add(new BigDecimal(0));
					}
				}
				setFixSalaryRecordBO.setProSalary(proSalaryOptions);
				setFixSalaryRecordBO.setProSum(proSum.toString());
				setFixSalaryRecordBO.setSalary(salaryOptions);
				setFixSalaryRecordBO.setSum(sum.toString());
				if (rowList.size() > (salaryOptions.size() + 7)) {
					setFixSalaryRecordBO.setRemarks(Optional.ofNullable(rowList.get(salaryOptions.size() + 7)).map(String::valueOf).orElse("0"));
				}
				archivesService.setFixSalaryRecord(setFixSalaryRecordBO);
				updateNum.incrementAndGet();
			} catch (CrmException e) {
				log.error("Import exception", e);
				rowList.add(0, e.getMsg());
				errorList.add(rowList);
			} catch (Exception e) {
				log.error("Import exception", e);
				rowList.add(0, "Unknown exception");
				errorList.add(rowList);
			}
		});
	}

}
