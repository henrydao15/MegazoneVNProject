package com.megazone.hrm.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.hrm.constant.TaxType;
import com.megazone.hrm.entity.BO.QuerySalaryPageListBO;
import com.megazone.hrm.entity.BO.SubmitExamineBO;
import com.megazone.hrm.entity.BO.UpdateSalaryBO;
import com.megazone.hrm.entity.PO.HrmSalaryMonthRecord;
import com.megazone.hrm.entity.VO.QuerySalaryPageListVO;
import com.megazone.hrm.entity.VO.SalaryOptionHeadVO;
import com.megazone.hrm.service.IHrmSalaryMonthRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@RestController
@RequestMapping("/hrmSalaryMonthRecord")
@Api(tags = "-")
@Slf4j
public class HrmSalaryMonthRecordController {


	@Autowired
	private IHrmSalaryMonthRecordService salaryMonthRecordService;


	@PostMapping("/querySalaryOptionHead")
	@ApiOperation("")
	public Result<List<SalaryOptionHeadVO>> querySalaryOptionHead() {
		List<SalaryOptionHeadVO> salaryOptionHeadVOList = salaryMonthRecordService.querySalaryOptionHead();
		return Result.ok(salaryOptionHeadVOList);
	}

	@PostMapping("/queryPaySalaryNumByType/{type}")
	@ApiOperation("")
	public Result<Integer> queryPaySalaryNumByType(@ApiParam(value = "0  1 ") @PathVariable("type") Integer type) {
		List<Map<String, Object>> mapList = salaryMonthRecordService.queryPaySalaryEmployeeListByType(type, null);
		return Result.ok(mapList.size());
	}

	@PostMapping("/queryNoPaySalaryEmployee")
	@ApiOperation("")
	public Result<List<Map<String, Object>>> queryNoPaySalaryEmployee() {
		List<Map<String, Object>> mapList = salaryMonthRecordService.queryNoPaySalaryEmployee();
		return Result.ok(mapList);
	}

	@PostMapping(value = "/downloadAttendanceTemp")
	@ApiOperation(value = "Download attendance import template", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadAttendanceTemp(HttpServletResponse response) {
		List<Map<String, Object>> mapList = salaryMonthRecordService.queryPaySalaryEmployeeListByType(1, null);
		mapList.forEach(map -> {
			map.remove("employeeId");
			map.remove("deptId");
			map.put("Overtime Pay", "");
			map.put("late charge", "");
			map.put("Early Refund", "");
			map.put("absenteeism deduction", "");
			map.put("Holiday deduction", "");
			map.put("Lack of card deduction", "");
			map.put("Comprehensive deduction", "");
			map.put("Actual Payable Days", "");
		});
		ExcelWriter writer = ExcelUtil.getWriter();
		writer.addHeaderAlias("employeeName", "employeeName");
		writer.addHeaderAlias("post", "post");
		writer.addHeaderAlias("jobNumber", "JobNumber");
		writer.addHeaderAlias("deptName", "Department");
		writer.addHeaderAlias("Overtime Pay", "Overtime Pay");
		writer.addHeaderAlias("Deduction for late arrival", "Deduction for late arrival");
		writer.addHeaderAlias("Early withdrawal deduction", "Early withdrawal deduction");
		writer.addHeaderAlias("Deduction for absenteeism", "Deduction for absenteeism");
		writer.addHeaderAlias("Vacation Deduction", "Vacation Deduction");
		writer.addHeaderAlias("Missing card deduction", "Lack of card deduction");
		writer.addHeaderAlias("Comprehensive deduction", "Comprehensive deduction");
		writer.addHeaderAlias("Actual Payable Days", "Actual Payable Days");
		writer.merge(11, "Employee attendance data template");
		for (int i = 0; i < 12; i++) {
			writer.setColumnWidth(i, 30);
		}
		writer.write(mapList, true);
		response.setContentType("application/octet-stream;charset=UTF-8");
		//Default Excel name
		response.setHeader("Content-Disposition", "attachment;filename=attendance_temp.xls");
		try (ServletOutputStream out = response.getOutputStream()) {
			writer.flush(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	@PostMapping(value = "/downCumulativeTaxOfLastMonthTemp")
	@ApiOperation(value = "Download the previous month's tax accumulation import template", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downCumulativeTaxOfLastMonthTemp(HttpServletResponse response) {
		List<Map<String, Object>> mapList = salaryMonthRecordService.queryPaySalaryEmployeeListByType(1, TaxType.SALARY_TAX_TYPE);
		mapList.forEach(map -> {
			map.remove("employeeId");
			map.remove("deptId");
			map.put("Accumulated income (as of last month)", "");
			map.put("Accumulated deduction expenses (as of last month)", "");
			map.put("Accumulated special deduction (as of last month)", "");
			map.put("Accumulated prepaid tax", "");
		});
		ExcelWriter writer = ExcelUtil.getWriter();
		writer.addHeaderAlias("employeeName", "employeeName");
		writer.addHeaderAlias("post", "post");
		writer.addHeaderAlias("jobNumber", "JobNumber");
		writer.addHeaderAlias("deptName", "Department");
		writer.addHeaderAlias("Accumulated income (as of last month)", "Accumulated income (as of last month)");
		writer.addHeaderAlias("Accumulated deduction fee (as of last month)", "Accumulated deduction fee (as of last month)");
		writer.addHeaderAlias("Cumulative special deduction (as of last month)", "cumulative special deduction (as of last month)");
		writer.addHeaderAlias("Accumulated Prepaid Tax", "Accumulated Prepaid Tax");
		writer.merge(8, "Last month's tax accumulation information data template");
		for (int i = 0; i < 9; i++) {
			writer.setColumnWidth(i, 30);
		}
		writer.write(mapList, true);
		response.setContentType("application/octet-stream;charset=UTF-8");
		//Default Excel name
		response.setHeader("Content-Disposition", "attachment;filename=cumulative_tax_of_last_month_temp.xls");
		try (ServletOutputStream out = response.getOutputStream()) {
			writer.flush(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	@PostMapping(value = "/downloadAdditionalDeductionTemp")
	@ApiOperation(value = "Download the cumulative import template for additional tax deductions", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadAdditionalDeductionTemp(HttpServletResponse response) {
		List<Map<String, Object>> mapList = salaryMonthRecordService.queryPaySalaryEmployeeListByType(1, TaxType.SALARY_TAX_TYPE);
		mapList.forEach(map -> {
			map.remove("employeeId");
			map.remove("deptId");
			map.put("Cumulative children's education", "");
			map.put("cumulative housing rent", "");
			map.put("Accumulated housing loan interest", "");
			map.put("Cumulative support for the elderly", "");
			map.put("Cumulative Continuing Education", "");
		});
		ExcelWriter writer = ExcelUtil.getWriter();
		writer.addHeaderAlias("employeeName", "employeeName");
		writer.addHeaderAlias("post", "post");
		writer.addHeaderAlias("jobNumber", "JobNumber");
		writer.addHeaderAlias("deptName", "Department");
		writer.addHeaderAlias("Cumulative Children's Education", "Cumulative Children's Education");
		writer.addHeaderAlias("cumulative housing rent", "cumulative housing rent");
		writer.addHeaderAlias("Accumulated Housing Loan Interest", "Accumulated Housing Loan Interest");
		writer.addHeaderAlias("cumulative support for the elderly", "cumulative support for the elderly");
		writer.addHeaderAlias("Cumulative Continuing Education", "Cumulative Continuing Education");
		writer.merge(8, "Additional tax deduction cumulative data template");
		for (int i = 0; i < 9; i++) {
			writer.setColumnWidth(i, 30);
		}
		writer.write(mapList, true);
		response.setContentType("application/octet-stream;charset=UTF-8");
		//Default Excel name
		response.setHeader("Content-Disposition", "attachment;filename=additional_deduction_temp.xls");
		try (ServletOutputStream out = response.getOutputStream()) {
			writer.flush(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	@PostMapping("/addNextMonthSalary")
	@ApiOperation("")
	public Result addNextMonthSalary() {
		salaryMonthRecordService.addNextMonthSalary();
		return Result.ok();
	}

	@PostMapping("/queryEmployeeChangeNum")
	@ApiOperation("")
	public Result<Map<Integer, Long>> queryEmployeeChangeNum() {
		Map<Integer, Long> map = salaryMonthRecordService.queryEmployeeChangeNum();
		return Result.ok(map);
	}

	@PostMapping("/queryLastSalaryMonthRecord")
	@ApiOperation("")
	public Result<HrmSalaryMonthRecord> queryLastSalaryMonthRecord() {
		HrmSalaryMonthRecord salaryMonthRecord = salaryMonthRecordService.queryLastSalaryMonthRecord();
		return Result.ok(salaryMonthRecord);
	}

	@PostMapping("/computeSalaryData")
	@ApiOperation("")
	public Result computeSalaryData(@ApiParam("id") @RequestParam("srecordId") Integer srecordId,
									@ApiParam("") @RequestParam("isSyncInsuranceData") Boolean isSyncInsuranceData,
									@ApiParam("") @RequestParam(name = "attendanceFile", required = false) MultipartFile attendanceFile,
									@ApiParam("") @RequestParam(name = "additionalDeductionFile", required = false) MultipartFile additionalDeductionFile,
									@ApiParam("") @RequestParam(name = "cumulativeTaxOfLastMonthFile", required = false) MultipartFile cumulativeTaxOfLastMonthFile
	) {
		salaryMonthRecordService.computeSalaryData(srecordId, isSyncInsuranceData, attendanceFile, additionalDeductionFile, cumulativeTaxOfLastMonthFile);
		return Result.ok();
	}

	@PostMapping("/querySalaryPageList")
	@ApiOperation("")
	public Result<BasePage<QuerySalaryPageListVO>> querySalaryPageList(@RequestBody QuerySalaryPageListBO querySalaryPageListBO) {
		BasePage<QuerySalaryPageListVO> page = salaryMonthRecordService.querySalaryPageList(querySalaryPageListBO);
		return Result.ok(page);
	}

	@PostMapping("/updateSalary")
	@ApiOperation("")
	public Result updateSalary(@RequestBody List<UpdateSalaryBO> updateSalaryBOList) {
		salaryMonthRecordService.updateSalary(updateSalaryBOList);
		return Result.ok();
	}

	@PostMapping("/querySalaryOptionCount/{sRecordId}")
	@ApiOperation("")
	public Result<List<Map<String, Object>>> querySalaryOptionCount(@PathVariable("sRecordId") String sRecordId) {
		List<Map<String, Object>> list = salaryMonthRecordService.querySalaryOptionCount(sRecordId);
		return Result.ok(list);
	}


	/**
	 * @return
	 */
	@PostMapping("/submitExamine")
	@ApiOperation("")
	public Result submitExamine(@RequestBody SubmitExamineBO submitExamineBO) {
		salaryMonthRecordService.submitExamine(submitExamineBO);
		return Result.ok();
	}

	@PostMapping("/deleteSalary/{sRecordId}")
	@ApiOperation("")
	public Result deleteSalary(@PathVariable Integer sRecordId) {
		salaryMonthRecordService.deleteSalary(sRecordId);
		return Result.ok();
	}


	//-------------feign,------
	@PostMapping("/querySalaryRecordById")
	@ApiExplain("id")
	public Result<HrmSalaryMonthRecord> querySalaryRecordById(@RequestParam("sRecordId") Integer sRecordId) {
		HrmSalaryMonthRecord salaryMonthRecord = salaryMonthRecordService.getById(sRecordId);
		return Result.ok(salaryMonthRecord);
	}

	@PostMapping("/updateCheckStatus")
	@ApiExplain("")
	public Result updateCheckStatus(@RequestParam("sRecordId") Integer sRecordId, @RequestParam("checkStatus") Integer checkStatus) {
		salaryMonthRecordService.updateCheckStatus(sRecordId, checkStatus);
		return Result.ok();
	}


}

