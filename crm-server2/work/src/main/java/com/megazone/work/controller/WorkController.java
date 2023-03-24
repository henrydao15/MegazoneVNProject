package com.megazone.work.controller;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.lang.Dict;
import cn.hutool.extra.servlet.ServletUtil;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.work.common.log.WorkLog;
import com.megazone.work.entity.BO.*;
import com.megazone.work.entity.PO.Work;
import com.megazone.work.entity.VO.TaskInfoVO;
import com.megazone.work.entity.VO.WorkInfoVo;
import com.megazone.work.service.IWorkCommonService;
import com.megazone.work.service.IWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
@RestController
@RequestMapping("/work")
@Api(tags = "")
@Slf4j
@SysLog(subModel = SubModelType.WORK_PROJECT, logClass = WorkLog.class)
public class WorkController {
	@Autowired
	private IWorkService workService;

	@Autowired
	private IWorkCommonService workCommonService;

	@PostMapping("/initWorkData")
	@ApiExplain("")
	public Result<Boolean> initWorkData() {
		return R.ok(workCommonService.initWorkData());
	}


	@PostMapping("/addWork")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#work.name", detail = "'Added items: '+#work.name")
	public Result<Work> addWork(@RequestBody Work work) {
		Work work1 = workService.addWork(work);
		return R.ok(work1);
	}

	@PostMapping("/updateWork")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE, object = "#work.name", detail = "'Edited items: '+#work.name")
	public Result<Work> updateWork(@RequestBody Work work) {
		Work work1 = workService.updateWork(work);
		return R.ok(work1);
	}

	@PostMapping("/getWorkById")
	@ApiOperation("")
	public Result<WorkInfoVo> getWorkById(@RequestParam("workId") Integer workId) {
		WorkInfoVo workInfoVo = workService.queryById(workId);
		return R.ok(workInfoVo);
	}

	@PostMapping("/deleteWork/{workId}")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteWork(@PathVariable @NotNull Integer workId) {
		workService.deleteWork(workId);
		return R.ok();
	}

	@PostMapping("/queryWorkNameList")
	@ApiOperation("")
	public Result queryWorkNameList(@RequestBody WorkTaskQueryBO workTaskQueryBO) {
		return R.ok(workService.queryWorkNameList(workTaskQueryBO));
	}

	@PostMapping("/queryWorkTaskList")
	@ApiOperation("")
	public Result<List<TaskInfoVO>> queryWorkAndTaskList(@RequestBody WorkTaskQueryBO workTaskQueryBO) {
		return R.ok(workService.queryWorkTaskList(workTaskQueryBO));
	}

	@PostMapping("/queryTaskByWorkId")
	@ApiOperation("")
	public Result queryTaskByWorkId(@RequestBody WorkTaskTemplateBO workTaskTemplateBO) {
		return R.ok(workService.queryTaskByWorkId(workTaskTemplateBO));
	}

	@PostMapping("/queryOwnerTaskListByWorkId")
	@ApiOperation("（）")
	public Result queryOwnerTaskListByWorkId(@RequestBody WorkTaskTemplateBO workTaskTemplateBO) {
		return R.ok(workService.queryOwnerTaskListByWorkId(workTaskTemplateBO));
	}

	@PostMapping("/queryTaskFileByWorkId")
	@ApiOperation("id")
	public Result<BasePage<FileEntity>> queryTaskFileByWorkId(@RequestBody QueryTaskFileByWorkIdBO QueryTaskFileByWorkIdBO) {
		BasePage<FileEntity> page = workService.queryTaskFileByWorkId(QueryTaskFileByWorkIdBO);
		return R.ok(page);
	}

	@PostMapping("/queryArchiveWorkList")
	@ApiOperation("")
	public Result queryArchiveWorkList(@RequestBody PageEntity pageEntity) {
		return R.ok(workService.queryArchiveWorkList(pageEntity));
	}

	@PostMapping("/workStatistics/{workId}")
	@ApiOperation("")
	public Result workStatistics(@PathVariable @NotNull String workId) {
		return R.ok(workService.workStatistics(workId));
	}

	@PostMapping("/queryWorkOwnerList/{workId}")
	@ApiOperation("")
	public Result queryWorkOwnerList(@PathVariable @NotNull Integer workId) {
		return R.ok(workService.queryWorkOwnerList(workId));
	}

	@PostMapping("/queryMemberList")
	@ApiOperation("")
	public Result queryMemberListByParticipateWork() {
		return R.ok(workService.queryMemberListByWorkOrTask(false));
	}

	@PostMapping("/updateOrder")
	@ApiOperation("")
	public Result updateOrder(@RequestBody UpdateTaskClassBO updateTaskClassBO) {
		workService.updateOrder(updateTaskClassBO);
		return R.ok();
	}

	@PostMapping("/leave/{workId}")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result leave(@PathVariable @NotNull Integer workId) {
		workService.leave(workId, UserUtil.getUserId());
		return R.ok();
	}

	@PostMapping("/removeWorkOwnerUser")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result removeWorkOwnerUser(@RequestBody RemoveWorkOwnerUserBO workOwnerUserBO) {
		workService.leave(workOwnerUserBO.getWorkId(), workOwnerUserBO.getOwnerUserId());
		return R.ok();
	}

	@PostMapping("/queryOwnerRoleList/{workId}")
	@ApiOperation("")
	public Result queryOwnerRoleList(@PathVariable @NotNull Integer workId) {
		return R.ok(workService.queryOwnerRoleList(workId));
	}


	@PostMapping("/setOwnerRole")
	@ApiOperation("")
	public Result setOwnerRole(@RequestBody SetWorkOwnerRoleBO setWorkOwnerRoleBO) {
		return R.ok(workService.setOwnerRole(setWorkOwnerRoleBO));
	}

	@PostMapping("/deleteTaskList")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteTaskList(@RequestBody DeleteTaskClassBO deleteTaskClassBO) {
		workService.deleteTaskList(deleteTaskClassBO);
		return R.ok();
	}

	@PostMapping("/archiveTask/{classId}")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.ARCHIVE, object = "Archiving completed tasks", detail = "Archiving completed tasks")
	public Result archiveTask(@PathVariable Integer classId) {
		workService.archiveTask(classId);
		return R.ok();
	}

	@PostMapping("/archiveTaskByOwner")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.ARCHIVE, object = "Archiving completed tasks", detail = "Archiving completed tasks")
	public Result archiveTaskByOwner(@RequestBody ArchiveTaskByOwnerBO archiveTaskByOwnerBO) {
		workService.archiveTask(archiveTaskByOwnerBO);
		return R.ok();
	}

	@PostMapping("/archList/{workId}")
	@ApiOperation("")
	public Result<List<TaskInfoVO>> archList(@PathVariable Integer workId) {
		List<TaskInfoVO> taskInfoVOS = workService.archList(workId);
		return R.ok(taskInfoVOS);
	}

	@PostMapping("/updateClassOrder")
	@ApiOperation("")
	public Result updateClassOrder(@RequestBody UpdateClassOrderBO updateClassOrderBO) {
		workService.updateClassOrder(updateClassOrderBO);
		return R.ok();
	}

	@PostMapping("/activation/{taskId}")
	@ApiOperation("")
	@SysLogHandler(subModel = SubModelType.WORK_TASK, behavior = BehaviorEnum.ACTIVE)
	public Result activation(@PathVariable Integer taskId) {
		workService.activation(taskId);
		return R.ok();
	}

	@PostMapping("/downloadExcel")
	@ApiOperation("Import Template")
	public void downloadExcel(HttpServletResponse response) {
		List<JSONObject> recordList = new LinkedList<>();
		recordList.add(new JSONObject().fluentPut("name", "task name").fluentPut("is_null", 1).fluentPut("type", 1));
		recordList.add(new JSONObject().fluentPut("name", "task description").fluentPut("is_null", 0).fluentPut("type", 1));
		recordList.add(new JSONObject().fluentPut("name", "start time").fluentPut("is_null", 0).fluentPut("type", 4));
		recordList.add(new JSONObject().fluentPut("name", "end time").fluentPut("is_null", 0).fluentPut("type", 4));
		recordList.add(new JSONObject().fluentPut("name", "responsible person").fluentPut("is_null", 0).fluentPut("type", 1));
		recordList.add(new JSONObject().fluentPut("name", "Participant").fluentPut("is_null", 0).fluentPut("type", 1));
		recordList.add(new JSONObject().fluentPut("name", "task list").fluentPut("is_null", 1).fluentPut("type", 1));
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Task import sheet");
		sheet.setDefaultRowHeight((short) 400);
		CellStyle textStyle = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		textStyle.setDataFormat(format.getFormat("@"));
		for (int i = 0; i < recordList.size(); i++) {
			if (Objects.equals(recordList.get(i).getInteger("type"), 4)) {
				CellStyle dateStyle = wb.createCellStyle();
				DataFormat dateFormat = wb.createDataFormat();
				dateStyle.setDataFormat(dateFormat.getFormat(DatePattern.NORM_DATE_PATTERN));
				sheet.setDefaultColumnStyle(i, dateStyle);
			} else {
				sheet.setDefaultColumnStyle(i, textStyle);
			}
			sheet.setColumnWidth(i, 20 * 256);
		}
		CellStyle cellStyle = wb.createCellStyle();
		HSSFRow titleRow = sheet.createRow(0);
		titleRow.setHeight((short) (6 * 256));
		cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		//Enable automatic line wrapping
		cellStyle.setWrapText(true);
		HSSFRichTextString title = new HSSFRichTextString("Task import template\n(*) is a required item\nThe person in charge must be a system user\nIf there are two task list names with the same name in the project, the first one is taken by default. If it does not exist, a new list will be created\nWhen there are multiple participants, please use an English comma");
		//Set two font styles in one cell
		Font firstFont = wb.createFont();
		firstFont.setFontHeightInPoints((short) 16);
		firstFont.setBold(true);
		Font secondFont = wb.createFont();
		secondFont.setFontHeightInPoints((short) 10);
		title.applyFont(0, 6, firstFont);
		title.applyFont(6, title.length(), secondFont);
		titleRow.createCell(0).setCellValue(title);
		titleRow.getCell(0).setCellStyle(cellStyle);
		CellRangeAddress region = new CellRangeAddress(0, 0, 0, recordList.size() - 1);
		sheet.addMergedRegion(region);
		try {
			HSSFRow row = sheet.createRow(1);
			for (int i = 0; i < recordList.size(); i++) {
				JSONObject record = recordList.get(i);
				//In the first cell of the first row, insert the option
				HSSFCell cell = row.createCell(i);
				// normal write operation
				if (record.getInteger("is_null") == 1) {
					cell.setCellValue(record.getString("name") + "(*)");
				} else {
					cell.setCellValue(record.getString("name"));
				}
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			//test.xls is the file name of the pop-up download dialog, which cannot be Chinese, please encode the Chinese by yourself
			response.setHeader("Content-Disposition", "attachment;filename=task_import.xls");
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

	/**
	 * excel import task
	 */
	@PostMapping("/excelImport")
	@ApiOperation("Import Template")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_IMPORT, object = "Import Task", detail = "Import Task")
	public Result<Dict> excelImport(@RequestParam("file") MultipartFile file, @RequestParam("workId") Integer workId) throws IOException {
		Dict dict = workService.excelImport(file, workId);
		return Result.ok(dict);
	}

	@PostMapping("/downloadErrorExcel")
	@ApiOperation("Download error data")
	public void downloadErrorExcel(@RequestParam("token") String token, HttpServletResponse response) {
		File file = new File(BaseUtil.UPLOAD_PATH + "excel/" + token);
		try {
			ServletUtil.write(response, new FileInputStream(file), "application/vnd.ms-excel;charset=utf-8", "task_import.xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/workTaskExport")
	@ApiOperation("Export task data")
	public void workTaskExport(@RequestParam("workId") Integer workId, HttpServletResponse response) {
		List<Map<String, Object>> list = workService.workTaskExport(workId);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("name", "task name"));
		dataList.add(ExcelParseUtil.toEntity("description", "task description"));
		dataList.add(ExcelParseUtil.toEntity("mainUserName", "Person in charge"));
		dataList.add(ExcelParseUtil.toEntity("startTime", "Start Time"));
		dataList.add(ExcelParseUtil.toEntity("stopTime", "End Time"));
		dataList.add(ExcelParseUtil.toEntity("labelName", "label"));
		dataList.add(ExcelParseUtil.toEntity("ownerUserName", "Participant"));
		dataList.add(ExcelParseUtil.toEntity("priority", "priority"));
		dataList.add(ExcelParseUtil.toEntity("createUserName", "Creator"));
		dataList.add(ExcelParseUtil.toEntity("createTime", "create time"));
		dataList.add(ExcelParseUtil.toEntity("className", "Where the task list"));
		dataList.add(ExcelParseUtil.toEntity("relateCrmWork", "Associated Business"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {
			}

			@Override
			public String getExcelName() {
				return "Project Task";
			}
		}, dataList, response);
	}

}
