package com.megazone.hrm.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.hrm.constant.ClockType;
import com.megazone.hrm.entity.BO.QueryAttendancePageBO;
import com.megazone.hrm.entity.PO.HrmAttendanceClock;
import com.megazone.hrm.entity.VO.QueryAttendancePageVO;
import com.megazone.hrm.service.IHrmAttendanceClockService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * Punch record sheet Front controller
 * </p>
 *
 * @author
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/hrmAttendanceClock")
@Slf4j
public class HrmAttendanceClockController {

	@Autowired
	private IHrmAttendanceClockService attendanceClockService;

	@PostMapping("/add")
	@ApiOperation("Add punch card")
	public Result add(@RequestBody HrmAttendanceClock attendanceClock) {
		attendanceClockService.addOrUpdate(attendanceClock);
		return Result.ok();
	}

	@PostMapping("/update")
	@ApiOperation("Modify punch card")
	public Result update(@RequestBody HrmAttendanceClock attendanceClock) {
		attendanceClockService.addOrUpdate(attendanceClock);
		return Result.ok();
	}

	@PostMapping("/delete/{clockId}")
	@ApiOperation("Delete punch card")
	public Result update(@PathVariable String clockId) {
		attendanceClockService.removeById(clockId);
		return Result.ok();
	}

	@PostMapping("/queryPageList")
	@ApiOperation("Query punch list")
	public Result<BasePage<QueryAttendancePageVO>> queryPageList(@RequestBody QueryAttendancePageBO attendancePageBO) {
		BasePage<QueryAttendancePageVO> page = attendanceClockService.queryPageList(attendancePageBO);
		return Result.ok(page);
	}

	@PostMapping("/queryMyPageList")
	@ApiOperation("Query your own punch list (used on mobile phone)")
	public Result<BasePage<QueryAttendancePageVO>> queryMyPageList(@RequestBody PageEntity pageEntity) {
		BasePage<QueryAttendancePageVO> page = attendanceClockService.queryMyPageList(pageEntity);
		return Result.ok(page);
	}

	@PostMapping("/excelExport")
	@ApiOperation("Export")
	public void excelExport(@RequestBody QueryAttendancePageBO attendancePageBO, HttpServletResponse response) {
		attendancePageBO.setPageType(0);
		List<QueryAttendancePageVO> list = attendanceClockService.queryPageList(attendancePageBO).getList();
		List<Map<String, Object>> collect = list.stream().map(clock -> {
			Map<String, Object> map = BeanUtil.beanToMap(clock);
			String clockType = ClockType.valueOfName((Integer) map.remove("clockType"));
			map.put("clockType", clockType);
			map.remove("clockId");
			map.remove("clockEmployeeId");
			map.remove("type");
			map.remove("lng");
			map.remove("lat");
			map.remove("remark");
			return map;
		}).collect(Collectors.toList());
		try (ExcelWriter writer = ExcelUtil.getWriter()) {
			writer.addHeaderAlias("employeeName", "Name");
			writer.addHeaderAlias("jobNumber", "JobNumber");
			writer.addHeaderAlias("deptName", "Department");
			writer.addHeaderAlias("post", "post");
			writer.addHeaderAlias("attendanceTime", "working hours");
			writer.addHeaderAlias("clockType", "Clock Type");
			writer.addHeaderAlias("clockTime", "clock time");
			writer.addHeaderAlias("address", "Punch location");
			writer.merge(9, "Employee Information");
			writer.setOnlyAlias(true);
			writer.write(collect, true);
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
			response.setHeader("Content-Disposition", "attachment;filename=attendance_clock.xls");
			ServletOutputStream out = response.getOutputStream();
			writer.flush(out);
		} catch (Exception e) {
			log.error("Export customer error: ", e);
		}
	}
}
