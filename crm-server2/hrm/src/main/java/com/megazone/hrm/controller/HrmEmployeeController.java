package com.megazone.hrm.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.megazone.core.common.JSON;
import com.megazone.core.common.JSONArray;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.utils.UserUtil;
import com.megazone.hrm.common.EmployeeHolder;
import com.megazone.hrm.constant.FieldTypeEnum;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.PO.*;
import com.megazone.hrm.entity.VO.DeptEmployeeListVO;
import com.megazone.hrm.entity.VO.EmployeeInfo;
import com.megazone.hrm.entity.VO.PersonalInformationVO;
import com.megazone.hrm.entity.VO.SimpleHrmEmployeeVO;
import com.megazone.hrm.service.HrmUploadExcelService;
import com.megazone.hrm.service.IHrmEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmEmployee")
@Api(tags = "")
@Slf4j
public class HrmEmployeeController {

	@Autowired
	private IHrmEmployeeService employeeService;

	@Autowired
	private HrmUploadExcelService excelService;

	@PostMapping("/queryLoginEmployee")
	@ApiOperation("")
	public Result<EmployeeInfo> queryLoginEmployee() {
		return Result.ok(EmployeeHolder.getEmployeeInfo());
	}

	@PostMapping("/addEmployee")
	@ApiOperation("")
	public Result addEmployee(@Valid @RequestBody AddEmployeeBO employeeVO) {
		employeeService.add(employeeVO);
		return Result.ok();
	}

	@PostMapping("/confirmEntry")
	@ApiOperation("")
	public Result confirmEntry(@RequestBody AddEmployeeFieldManageBO employeeBO) {
		employeeService.confirmEntry(employeeBO);
		return Result.ok();
	}


	@PostMapping("/queryEmployeeStatusNum")
	@ApiOperation("")
	public Result<Map<Integer, Long>> queryEmployeeStatusNum() {
		Map<Integer, Long> statusMap = employeeService.queryEmployeeStatusNum();
		return Result.ok(statusMap);
	}

	@PostMapping("/queryPageList")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody QueryEmployeePageListBO employeePageListBO) {
		BasePage<Map<String, Object>> map = employeeService.queryPageList(employeePageListBO);
		return Result.ok(map);
	}

	@PostMapping("/queryAllEmployeeList")
	@ApiOperation("()")
	public Result<List<SimpleHrmEmployeeVO>> queryAllEmployeeList() {
		List<SimpleHrmEmployeeVO> list = employeeService.queryAllEmployeeList();
		return Result.ok(list);
	}

	@PostMapping("/queryDeptEmployeeList/{deptId}")
	@ApiOperation("")
	public Result<DeptEmployeeListVO> queryDeptEmployeeList(@PathVariable Integer deptId) {
		DeptEmployeeListVO deptEmployeeListVO = employeeService.queryDeptEmployeeList(deptId);
		return Result.ok(deptEmployeeListVO);
	}

	@PostMapping("/queryDeptEmpListByUser")
	@ApiOperation("(hrm)")
	public Result<Set<SimpleHrmEmployeeVO>> queryDeptEmpListByUser(@RequestBody DeptUserListByUserBO deptUserListByUserBO) {
		Set<SimpleHrmEmployeeVO> userList = employeeService.queryDeptUserListByUser(deptUserListByUserBO);
		return Result.ok(userList);
	}

	@PostMapping("/queryInEmployeeList")
	@ApiOperation("()")
	public Result<List<SimpleHrmEmployeeVO>> queryInEmployeeList() {
		List<SimpleHrmEmployeeVO> list = employeeService.queryInEmployeeList();
		return Result.ok(list);
	}


	@PostMapping("/personalInformation/{employeeId}")
	@ApiOperation("")
	public Result<PersonalInformationVO> personalInformation(@PathVariable("employeeId") Integer employeeId) {
		PersonalInformationVO personalInformationVO = employeeService.personalInformation(employeeId);
		return Result.ok(personalInformationVO);
	}

	@PostMapping("/queryById/{employeeId}")
	@ApiOperation("")
	public Result<HrmEmployee> queryById(@PathVariable("employeeId") Integer employeeId) {
		HrmEmployee hrmEmployee = employeeService.queryById(employeeId);
		return Result.ok(hrmEmployee);
	}

	@PostMapping("/updateInformation")
	@ApiOperation("")
	public Result updateInformation(@RequestBody UpdateInformationBO updateInformationBO) {
		employeeService.updateInformation(updateInformationBO);
		return Result.ok();
	}


	@PostMapping("/updateCommunication")
	@ApiOperation("")
	public Result updateCommunication(@RequestBody UpdateInformationBO updateInformationBO) {
		employeeService.updateCommunication(updateInformationBO);
		return Result.ok();
	}


	@PostMapping("/addExperience")
	@ApiOperation("")
	public Result addOrUpdateEduExperience(@Validated @RequestBody HrmEmployeeEducationExperience educationExperience) {
		employeeService.addOrUpdateEduExperience(educationExperience);
		return Result.ok();
	}

	@PostMapping("/setEduExperience")
	@ApiOperation("")
	public Result setEduExperience(@Validated @RequestBody HrmEmployeeEducationExperience educationExperience) {
		employeeService.addOrUpdateEduExperience(educationExperience);
		return Result.ok();
	}


	@PostMapping("/deleteEduExperience/{educationId}")
	@ApiOperation("")
	public Result deleteEduExperience(@PathVariable("educationId") Integer educationId) {
		employeeService.deleteEduExperience(educationId);
		return Result.ok();
	}

	@PostMapping("/addWorkExperience")
	@ApiOperation("")
	public Result addWorkExperience(@Validated @RequestBody HrmEmployeeWorkExperience workExperience) {
		employeeService.addOrUpdateWorkExperience(workExperience);
		return Result.ok();
	}

	@PostMapping("/setWorkExperience")
	@ApiOperation("")
	public Result setWorkExperience(@Validated @RequestBody HrmEmployeeWorkExperience workExperience) {
		employeeService.addOrUpdateWorkExperience(workExperience);
		return Result.ok();
	}


	@PostMapping("/deleteWorkExperience/{workExpId}")
	@ApiOperation("")
	public Result deleteWorkExperience(@PathVariable("workExpId") Integer workExpId) {
		employeeService.deleteWorkExperience(workExpId);
		return Result.ok();
	}


	@PostMapping("/addCertificate")
	@ApiOperation("")
	public Result addCertificate(@Validated @RequestBody HrmEmployeeCertificate certificate) {
		employeeService.addOrUpdateCertificate(certificate);
		return Result.ok();
	}

	@PostMapping("/setCertificate")
	@ApiOperation("")
	public Result setCertificate(@Validated @RequestBody HrmEmployeeCertificate certificate) {
		employeeService.addOrUpdateCertificate(certificate);
		return Result.ok();
	}

	@PostMapping("/deleteCertificate/{certificateId}")
	@ApiOperation("")
	public Result deleteCertificate(@PathVariable Integer certificateId) {
		employeeService.deleteCertificate(certificateId);
		return Result.ok();
	}


	@PostMapping("/addTrainingExperience")
	@ApiOperation("")
	public Result addTrainingExperience(@Validated @RequestBody HrmEmployeeTrainingExperience trainingExperience) {
		employeeService.addOrUpdateTrainingExperience(trainingExperience);
		return Result.ok();
	}

	@PostMapping("/setTrainingExperience")
	@ApiOperation("")
	public Result setTrainingExperience(@Validated @RequestBody HrmEmployeeTrainingExperience trainingExperience) {
		employeeService.addOrUpdateTrainingExperience(trainingExperience);
		return Result.ok();
	}

	@PostMapping("/deleteTrainingExperience/{trainingId}")
	@ApiOperation("")
	public Result deleteTrainingExperience(@PathVariable Integer trainingId) {
		employeeService.deleteTrainingExperience(trainingId);
		return Result.ok();
	}

	@PostMapping("/queryContactsAddField")
	@ApiOperation("")
	public Result<List<HrmEmployeeField>> queryContactsAddField() {
		List<HrmEmployeeField> hrmEmployeeFieldList = employeeService.queryContactsAddField();
		return Result.ok(hrmEmployeeFieldList);
	}


	@PostMapping("/addContacts")
	@ApiOperation("")
	public Result addContacts(@RequestBody UpdateInformationBO updateInformationBO) {
		employeeService.addOrUpdateContacts(updateInformationBO);
		return Result.ok();
	}

	@PostMapping("/setContacts")
	@ApiOperation("")
	public Result setContacts(@RequestBody UpdateInformationBO updateInformationBO) {
		employeeService.addOrUpdateContacts(updateInformationBO);
		return Result.ok();
	}


	@PostMapping("/deleteContacts/{contractsId}")
	@ApiOperation("")
	public Result deleteContacts(@PathVariable("contractsId") Integer contractsId) {
		employeeService.deleteContacts(contractsId);
		return Result.ok();
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("")
	public Result deleteByIds(@RequestBody List<Integer> employeeIds) {
		employeeService.deleteByIds(employeeIds);
		return Result.ok();
	}


	@PostMapping("/become")
	@ApiOperation("")
	public Result become(@RequestBody HrmEmployeeChangeRecord hrmEmployeeChangeRecord) {
		employeeService.change(hrmEmployeeChangeRecord);
		return Result.ok();
	}

	@PostMapping("/changePost")
	@ApiOperation("/")
	public Result changePost(@RequestBody HrmEmployeeChangeRecord hrmEmployeeChangeRecord) {
		employeeService.change(hrmEmployeeChangeRecord);
		return Result.ok();
	}

	@PostMapping("/promotion")
	@ApiOperation("/")
	public Result promotion(@RequestBody HrmEmployeeChangeRecord hrmEmployeeChangeRecord) {
		employeeService.change(hrmEmployeeChangeRecord);
		return Result.ok();
	}

	@PostMapping("/updateInsuranceScheme")
	@ApiOperation("")
	public Result updateInsuranceScheme(@RequestBody UpdateInsuranceSchemeBO updateInsuranceSchemeBO) {
		employeeService.updateInsuranceScheme(updateInsuranceSchemeBO);
		return Result.ok();
	}


	@PostMapping("/againOnboarding")
	@ApiOperation("")
	public Result againOnboarding(@RequestBody AddEmployeeFieldManageBO employeeBO) {
		employeeService.againOnboarding(employeeBO);
		return Result.ok();
	}

	/**
	 * @author wyq
	 */
	@PostMapping("/downloadExcel")
	@ApiOperation("")
	public void downloadExcel(HttpServletResponse response) {
		List<HrmEmployeeField> hrmEmployeeFields = employeeService.downloadExcelFiled();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Employee Import Sheet");
		sheet.setDefaultRowHeight((short) 400);
		CellStyle textStyle = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		textStyle.setDataFormat(format.getFormat("@"));
		for (int i = 0; i < hrmEmployeeFields.size(); i++) {
			HrmEmployeeField hrmEmployeeField = hrmEmployeeFields.get(i);
			if (Objects.equals(hrmEmployeeField.getType(), FieldTypeEnum.DATE.getValue())) {
				CellStyle dateStyle = wb.createCellStyle();
				DataFormat dateFormat = wb.createDataFormat();
				dateStyle.setDataFormat(dateFormat.getFormat("yyyy\"year\"m\"month\"d\"day\""));
				sheet.setDefaultColumnStyle(i, dateStyle);
			} else if (Objects.equals(hrmEmployeeField.getType(), FieldTypeEnum.DATETIME.getValue())) {
				CellStyle dateStyle = wb.createCellStyle();
				DataFormat dateFormat = wb.createDataFormat();
				dateStyle.setDataFormat(dateFormat.getFormat(DatePattern.NORM_DATETIME_PATTERN));
				sheet.setDefaultColumnStyle(i, dateStyle);
			} else {
				sheet.setDefaultColumnStyle(i, textStyle);
			}
			sheet.setColumnWidth(i, 20 * 256);
		}
		CellStyle cellStyle = wb.createCellStyle();
		HSSFRow titleRow = sheet.createRow(0);
		cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 16);
		cellStyle.setFont(font);
		titleRow.createCell(0).setCellValue("Employee import template (*) is required");
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		titleRow.getCell(0).setCellStyle(cellStyle);
		CellRangeAddress region = new CellRangeAddress(0, 0, 0, hrmEmployeeFields.size() - 1);
		sheet.addMergedRegion(region);
		try {
			HSSFRow row = sheet.createRow(1);
			for (int i = 0; i < hrmEmployeeFields.size(); i++) {
				HrmEmployeeField hrmEmployeeField = hrmEmployeeFields.get(i);
				String options = hrmEmployeeField.getOptions();
				//In the first cell of the first row, insert the option
				HSSFCell cell = row.createCell(i);
				// normal write operation
				String cellValue;
				if (hrmEmployeeField.getIsNull() == 1) {
					cellValue = hrmEmployeeField.getName() + "(*)";
				} else {
					cellValue = hrmEmployeeField.getName();
				}
				if (StrUtil.isNotEmpty(hrmEmployeeField.getRemark())) {
					cellValue += "(" + hrmEmployeeField.getRemark() + ")";
				}
				if (hrmEmployeeField.getType().equals(FieldTypeEnum.DATE.getValue())) {
					cellValue += "-Example: [October 1, 2020]";
				}
				if ("dept_id".equals(hrmEmployeeField.getFieldName())) {
					cellValue += "-(organization code)";
				}
				cell.setCellValue(cellValue);
				if (StrUtil.isNotEmpty(options)) {
					List<String> setting = new ArrayList<>();
					JSONArray array = JSON.parseArray(options);
					for (int j = 0; j < JSON.parseArray(options).size(); j++) {
						setting.add(array.getJSONObject(j).getString("name"));
					}
					String fieldName = hrmEmployeeField.getFieldName();
					HSSFSheet hidden = wb.createSheet(fieldName);
					HSSFCell sheetCell = null;
					for (int j = 0, length = setting.size(); j < length; j++) {
						String name = setting.get(j);
						HSSFRow sheetRow = hidden.createRow(j);
						sheetCell = sheetRow.createCell(0);
						sheetCell.setCellValue(name);
					}
					Name namedCell = wb.createName();
					namedCell.setNameName(fieldName);
					namedCell.setRefersToFormula(fieldName + "!$A$1:$A$" + setting.size());
					CellRangeAddressList regions = new CellRangeAddressList(2, Integer.MAX_VALUE, i, i);
					DVConstraint constraint = DVConstraint.createFormulaListConstraint(fieldName);
					HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
					wb.setSheetHidden(wb.getSheetIndex(hidden), true);
					sheet.addValidationData(dataValidation);
				}
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			//test.xls is the file name of the pop-up download dialog, which cannot be Chinese, please encode the Chinese by yourself
			response.setHeader("Content-Disposition", "attachment;filename=employee_import.xls");
			wb.write(response.getOutputStream());

		} catch (Exception e) {
			log.error("error", e);
		} finally {
			try {
				wb.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@PostMapping("/uploadExcel")
	@ApiOperation("")
	public Result<Long> uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam("repeatHandling") Integer repeatHandling) {
		UploadExcelBO uploadExcelBO = new UploadExcelBO();
		uploadExcelBO.setRepeatHandling(repeatHandling);
		Long messageId = excelService.uploadExcel(file, uploadExcelBO);
		return Result.ok(messageId);
	}

	@PostMapping("/export")
	@ApiOperation("Export employee")
	public void export(@RequestBody QueryEmployeePageListBO employeePageListBO, HttpServletResponse response) {
		List<Map<String, Object>> list = employeeService.export(employeePageListBO);
		try (ExcelWriter writer = ExcelUtil.getWriter()) {
			writer.addHeaderAlias("employeeName", "Name");
			writer.addHeaderAlias("jobNumber", "JobNumber");
			writer.addHeaderAlias("mobile", "mobile phone number");
			writer.addHeaderAlias("deptName", "Department");
			writer.addHeaderAlias("post", "post");
			writer.addHeaderAlias("employmentForms", "employment forms");
			writer.addHeaderAlias("status", "Employee Status");
			writer.addHeaderAlias("entryTime", "Entry Date");
			writer.addHeaderAlias("idType", "Document Type");
			writer.addHeaderAlias("idNumber", "IDNumber");
			writer.merge(9, "Employee Information");
			writer.setOnlyAlias(true);
			writer.write(list, true);
			writer.setRowHeight(0, 30);
			writer.setRowHeight(1, 20);
			for (int i = 0; i < 10; i++) {
				writer.setColumnWidth(i, 20);
			}
			Cell cell = writer.getCell(0, 0);
			CellStyle cellStyle = cell.getCellStyle();
			cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = writer.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) 16);
			cellStyle.setFont(font);
			cell.setCellStyle(cellStyle);
			// custom title alias
			//response is the HttpServletResponse object
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			//test.xls is the file name of the pop-up download dialog, which cannot be Chinese, please encode the Chinese by yourself
			response.setHeader("Content-Disposition", "attachment;filename=employee.xls");
			ServletOutputStream out = response.getOutputStream();
			writer.flush(out);
		} catch (Exception e) {
			log.error("Export customer error: ", e);
		}
	}

	@PostMapping("/adminAddEmployee")
	@ApiOperation("")
	public Result adminAddEmployee(@RequestBody List<AddEmployeeBO> employeeList) {
		employeeService.adminAddEmployee(employeeList);
		return Result.ok();
	}

	@PostMapping("/queryIsInHrm")
	@ApiOperation("")
	public Result<Boolean> queryIsInHrm() {
		EmployeeInfo employeeInfo = employeeService.queryEmployeeInfoByMobile(UserUtil.getUser().getUsername());
		return Result.ok(Objects.nonNull(employeeInfo));
	}

	@PostMapping("/field")
	@ApiOperation("")
	public Result<List> queryEmployeeField(@ApiParam(" 1  2 ") @RequestParam(value = "entryStatus") Integer entryStatus) {
		return R.ok(employeeService.queryEmployeeField(entryStatus));
	}

	@PostMapping("/addEmployeeField")
	@ApiOperation("")
	public Result addEmployee(@RequestBody AddEmployeeFieldManageBO addEmployeeFieldManageBO) {
		employeeService.addEmployeeField(addEmployeeFieldManageBO);
		return Result.ok();
	}
}

