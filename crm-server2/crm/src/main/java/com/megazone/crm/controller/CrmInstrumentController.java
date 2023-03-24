package com.megazone.crm.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmSearchParamsBO;
import com.megazone.crm.entity.PO.CrmActivity;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.CrmInstrumentService;
import com.megazone.crm.service.ICrmInstrumentSortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * CRM
 * </p>
 *
 * @author
 * @since 2020-05-25
 */
@Slf4j
@RestController
@RequestMapping("/crmInstrument/")
@Api(tags = "")
public class CrmInstrumentController {

	@Autowired
	private ICrmInstrumentSortService sortService;

	@Autowired
	private CrmInstrumentService instrumentService;


	@PostMapping("/queryModelSort")
	@ApiOperation("")
	public Result<JsonNode> queryModelSort() {
		JSONObject object = sortService.queryModelSort();
		return Result.ok(object.node);
	}

	@PostMapping("/setModelSort")
	@ApiOperation("")
	public Result setModelSort(@RequestBody JSONObject jsonObject) {
		sortService.setModelSort(jsonObject);
		return R.ok();
	}

	@PostMapping("/queryBulletin")
	@ApiOperation("")
	public Result<JsonNode> queryBulletin(@RequestBody BiParams biParams) {
		JSONObject jsonObject = instrumentService.queryBulletin(biParams);
		return R.ok(jsonObject.node);
	}

	@PostMapping("/queryBulletinInfo")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> queryBulletinInfo(@RequestBody BiParams biParams) {
		BasePage<Map<String, Object>> basePage = instrumentService.queryBulletinInfo(biParams);
		return R.ok(basePage);
	}

	@PostMapping("/forgottenCustomerCount")
	@ApiOperation("")
	public Result<JsonNode> forgottenCustomerCount(@RequestBody BiParams biParams) {
		JSONObject jsonObject = instrumentService.forgottenCustomerCount(biParams);
		return R.ok(jsonObject.node);
	}

	@PostMapping("/sellFunnel")
	@ApiOperation("")
	public Result<JsonNode> sellFunnel(@RequestBody BiParams biParams) {
		JSONObject jsonObject = instrumentService.sellFunnel(biParams);
		return R.ok(jsonObject.node);
	}


	@PostMapping("/sellFunnelBusinessList")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> sellFunnelBusinessList(@RequestBody CrmSearchParamsBO crmSearchParamsBO) {
		BasePage<Map<String, Object>> mapBasePage = instrumentService.sellFunnelBusinessList(crmSearchParamsBO);
		return R.ok(mapBasePage);
	}

	@PostMapping("/salesTrend")
	@ApiOperation("")
	public Result<JsonNode> salesTrend(@RequestBody BiParams biParams) {
		JSONObject jsonObject = instrumentService.salesTrend(biParams);
		return R.ok(jsonObject.node);
	}

	@PostMapping("/queryDataInfo")
	@ApiOperation("")
	public Result<JsonNode> queryDataInfo(@RequestBody BiParams biParams) {
		JSONObject jsonObject = instrumentService.queryDataInfo(biParams);
		return R.ok(jsonObject.node);
	}

	@PostMapping("/queryPerformance")
	@ApiOperation("")
	public Result<JsonNode> queryPerformance(@RequestBody BiParams biParams) {
		JSONObject jsonObject = instrumentService.queryPerformance(biParams);
		return R.ok(jsonObject.node);
	}


	@PostMapping("/queryRecordList")
	@ApiOperation("")
	public Result<BasePage<CrmActivity>> queryRecordList(@RequestBody BiParams biParams) {
		BasePage<CrmActivity> page = instrumentService.queryRecordList(biParams);
		return Result.ok(page);
	}


	@PostMapping("/queryRecordCount")
	@ApiOperation("")
	public Result<List<JSONObject>> queryRecordCount(@RequestBody BiParams biParams) {
		List<JSONObject> data = instrumentService.queryRecordCount(biParams);
		return Result.ok(data);
	}

	/**
	 *
	 */
	@PostMapping("/forgottenCustomerPageList")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> forgottenCustomerPageList(@RequestBody BiParams biParams) {
		BasePage<Map<String, Object>> page = instrumentService.forgottenCustomerPageList(biParams);
		return Result.ok(page);
	}


	@PostMapping("/unContactCustomerPageList")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> unContactCustomerPageList(@RequestBody BiParams biParams) {
		BasePage<Map<String, Object>> page = instrumentService.unContactCustomerPageList(biParams);
		return Result.ok(page);
	}


	@PostMapping("/importRecordList")
	@ApiOperation("excel")
//	@SysLogHandler(behavior = BehaviorEnum.EXCEL_IMPORT, object = "excel", detail = "excel")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_IMPORT, object = "Import excel follow-up records", detail = "Import excel follow-up records")
	public Result<JsonNode> importRecordList(@RequestParam("file") MultipartFile file, @RequestParam("crmType") Integer crmType) {
		JSONObject object = instrumentService.importRecordList(file, crmType);
		return R.ok(object.node);
	}

	@PostMapping("/downloadRecordExcel")
	@ApiOperation("Get import template")
	public void downloadExcel(HttpServletResponse response, @RequestParam("crmType") Integer crmType) {
		//Customer: follow-up content (required), creator (required), customer (required), follow-up time, follow-up method, related contacts, related business opportunities
		//Non-customers: follow-up content (required), creator (required), lead/contact/business opportunity/contract (required), follow-up time, follow-up method
		if (!Arrays.asList(1, 2, 3, 5, 6).contains(crmType)) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		CrmEnum crmEnum = CrmEnum.parse(crmType);
		List<CrmModelFiledVO> filedList = new ArrayList<>();
		filedList.add(new CrmModelFiledVO("content", FieldEnum.TEXT, "*follow-up content", 1));
		filedList.add(new CrmModelFiledVO("createUserName", FieldEnum.TEXT, "*Creator", 1));
		if (crmType == 2) {
			filedList.add(new CrmModelFiledVO("crmTypeName", FieldEnum.TEXT, "*client", 1));
		} else {
			filedList.add(new CrmModelFiledVO("crmTypeName", FieldEnum.TEXT, "*owns" + crmEnum.getRemarks(), 1));
		}
		filedList.add(new CrmModelFiledVO("createTime", FieldEnum.DATE, "Follow-up time - example: 2020-2-1", 1));
		filedList.add(new CrmModelFiledVO("category", FieldEnum.TEXT, "Follow-up method", 1));
		if (crmType == 2) {
			filedList.add(new CrmModelFiledVO("contactsNames", FieldEnum.TEXT, "Related Contacts", 1));
			filedList.add(new CrmModelFiledVO("businessNames", FieldEnum.TEXT, "Related Business Opportunities", 1));
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Follow up record import sheet");
		sheet.setDefaultRowHeight((short) 400);
		CellStyle textStyle = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		textStyle.setDataFormat(format.getFormat("@"));
		for (int i = 0; i < filedList.size(); i++) {
			CrmModelFiledVO crmModelFiledVO = filedList.get(i);
			sheet.setDefaultColumnStyle(i, textStyle);
			sheet.setColumnWidth(i, 20 * 256);
		}
		try {
			HSSFRow hintRow = sheet.createRow(0);
			String desc = CrmEnum.CONTRACT.equals(crmEnum) ? "Number" : "Name";
			hintRow.createCell(0).setCellValue("Note:\n" + "1. The red font marked \"*\" in the header is required\n" + "2. Follow-up time: The recommended format is 2020-2-1\n" + "3. If there are multiple pieces of relevant data, use to distinguish for example: Hangzhou Technology Co., Ltd. / Kakarot Software Technology Co., Ltd.\n" + "4. Belonging" + crmEnum.getRemarks() + "" + crmEnum.getRemarks() + "need to exist in the system," + "And the filled in " + crmEnum.getRemarks() + desc + " must be consistent with " + crmEnum.getRemarks() + desc + " in the system, otherwise the import will fail\n" + "5. The creator is a system employee, please fill in the \"name\" of the system employee. If the " + "system employee cannot be matched, the import will fail\n" + "6. If there are multiple " + desc + " duplicates in the system, it will be imported into the latest data by default");
			hintRow.setHeight((short) 2100);
			CellStyle hintStyle = wb.createCellStyle();
			hintStyle.setWrapText(true);
			hintStyle.setAlignment(HorizontalAlignment.LEFT);
			hintRow.getCell(0).setCellStyle(hintStyle);
			CellRangeAddress hintRegion = new CellRangeAddress(0, 0, 0, filedList.size() - 1);
			sheet.addMergedRegion(hintRegion);

			HSSFRow row = sheet.createRow(1);
			row.setHeight((short) 400);
			for (int i = 0; i < filedList.size(); i++) {
				CrmModelFiledVO crmModelFiledVO = filedList.get(i);
				//In the first cell of the first row, insert the option
				HSSFCell cell = row.createCell(i);
				if (crmModelFiledVO.getName().contains("*")) {
					HSSFRichTextString richString = new HSSFRichTextString(crmModelFiledVO.getName());
					HSSFFont wbFont = wb.createFont();
					wbFont.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
					richString.applyFont(wbFont);
					cell.setCellValue(richString);
					continue;
				}
				// normal write operation
				String cellValue = crmModelFiledVO.getName();
				cell.setCellValue(cellValue);
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			//test.xls is the file name of the pop-up download dialog, which cannot be Chinese, please encode the Chinese by yourself
			response.setHeader("Content-Disposition", "attachment;filename=record_import.xls");
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

	@PostMapping("/exportRecordList")
	@ApiOperation("Export follow-up records")
	public void exportRecordList(@RequestBody BiParams biParams, HttpServletResponse response) {
		Integer crmType = biParams.getLabel();
		if (!Arrays.asList(1, 2, 3, 5, 6).contains(crmType)) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		CrmEnum crmEnum = CrmEnum.parse(crmType);
		List<Map<String, Object>> objectList = instrumentService.exportRecordList(biParams);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		if (crmType == 2) {
			dataList.add(ExcelParseUtil.toEntity("crmTypeName", "owning customer"));
		}
		dataList.add(ExcelParseUtil.toEntity("content", "Follow-up content"));
		dataList.add(ExcelParseUtil.toEntity("createUserName", "Follower"));
		if (crmType != 2) {
			dataList.add(ExcelParseUtil.toEntity("crmTypeName", "own" + crmEnum.getRemarks()));
		}
		dataList.add(ExcelParseUtil.toEntity("createTime", "Follow up time"));
		dataList.add(ExcelParseUtil.toEntity("category", "Follow-up method"));
		dataList.add(ExcelParseUtil.toEntity("nextTime", "Next contact time"));
		if (crmType == 2) {
			dataList.add(ExcelParseUtil.toEntity("contactsNames", "Related Contacts"));
			dataList.add(ExcelParseUtil.toEntity("businessNames", "Related Business Opportunities"));
		}
		ExcelParseUtil.exportExcel(objectList, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Follow up record";
			}
		}, dataList);

	}

}
