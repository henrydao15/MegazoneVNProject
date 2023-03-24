package com.megazone.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ExcelParseUtil {

	/**
	 *
	 */
	private static final List<Integer> typeList = Arrays.asList(FieldEnum.FILE.getType(), FieldEnum.CHECKBOX.getType(), FieldEnum.USER.getType(), FieldEnum.STRUCTURE.getType(),
			FieldEnum.AREA.getType(), FieldEnum.AREA_POSITION.getType(), FieldEnum.CURRENT_POSITION.getType(), FieldEnum.DATE_INTERVAL.getType(), FieldEnum.BOOLEAN_VALUE.getType(),
			FieldEnum.HANDWRITING_SIGN.getType(), FieldEnum.DESC_TEXT.getType(), FieldEnum.DETAIL_TABLE.getType(), FieldEnum.CALCULATION_FUNCTION.getType()
	);

	public static void exportExcel(List<? extends Map<String, Object>> dataList, ExcelParseService excelParseService, List<?> list) {
		exportExcel(dataList, excelParseService, list, BaseUtil.getResponse());
	}

	public static void exportExcel(List<? extends Map<String, Object>> dataList, ExcelParseService excelParseService, List<?> list, HttpServletResponse response) {
		try (ExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getWriter(excelParseService.isXlsx())) {
			List<ExcelDataEntity> headList = excelParseService.parseData(list, false);
			Map<String, Integer> headMap = new HashMap<>(headList.size(), 1.0f);
			headList.forEach(head -> {
				writer.addHeaderAlias(head.getFieldName(), head.getName());
				if (Arrays.asList(FieldEnum.AREA.getType(), FieldEnum.AREA_POSITION.getType(), FieldEnum.CURRENT_POSITION.getType(), FieldEnum.DETAIL_TABLE.getType()).contains(head.getType())) {
					headMap.put(head.getFieldName(), head.getType());
				}
			});
			//
			CellStyle cellStyle = writer.getCellStyle();
			cellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderTop(BorderStyle.NONE);
			cellStyle.setBorderBottom(BorderStyle.NONE);
			cellStyle.setBorderLeft(BorderStyle.NONE);
			cellStyle.setBorderRight(BorderStyle.NONE);
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
			Font defaultFont = writer.createFont();
			defaultFont.setFontHeightInPoints((short) 11);
			cellStyle.setFont(defaultFont);
			//
			CellStyle cellStyleForNumber = writer.getStyleSet().getCellStyleForNumber();
			cellStyleForNumber.setBorderTop(BorderStyle.NONE);
			cellStyleForNumber.setBorderBottom(BorderStyle.NONE);
			cellStyleForNumber.setBorderLeft(BorderStyle.NONE);
			cellStyleForNumber.setBorderRight(BorderStyle.NONE);
			cellStyleForNumber.setAlignment(HorizontalAlignment.LEFT);
			cellStyleForNumber.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
			cellStyleForNumber.setFont(defaultFont);
			//
			dataList.forEach(record -> excelParseService.castData(record, headMap));

			writer.setRowHeight(-1, 20);
			writer.setColumnWidth(-1, 20);

			writer.setOnlyAlias(true);
			if (dataList.size() == 0) {
				Map<String, Object> record = new HashMap<>();
				headList.forEach(head -> record.put(head.getFieldName(), ""));
				writer.write(Collections.singletonList(record), true);
			} else {
				writer.write(dataList, true);
			}
			CellStyle style = writer.getHeadCellStyle();
			style.setAlignment(HorizontalAlignment.LEFT);
			Font font = writer.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) 11);
			style.setFont(font);

			//responseHttpServletResponse
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			//test.xls，，
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(excelParseService.getExcelName() + "info", "utf-8") + ".xls" + (excelParseService.isXlsx() ? "x" : ""));
			ServletOutputStream out = response.getOutputStream();
			writer.flush(out);
		} catch (Exception e) {
			log.error("：", e);
		}
	}

	public static void importExcel(ExcelParseService excelParseService, List<?> list) {
		importExcel(excelParseService, list, BaseUtil.getResponse(), null);
	}

	public static void importExcel(ExcelParseService excelParseService, List<?> list, HttpServletResponse response, String module) {
		List<ExcelDataEntity> dataEntities = excelParseService.parseData(list, true);
		try (ExcelWriter writer = ExcelUtil.getWriter(excelParseService.isXlsx())) {
			//Because repeated merging of cells will result in loss of styles, first get all fields and merge them once
			int sum = dataEntities.stream().mapToInt(data -> excelParseService.addCell(null, 0, 0, data.getFieldName())).sum();
			writer.renameSheet(excelParseService.getExcelName() + "Import Template");
			writer.merge(dataEntities.size() - 1 + sum, excelParseService.getMergeContent(module), true);
			writer.getHeadCellStyle().setAlignment(HorizontalAlignment.LEFT);
			writer.getHeadCellStyle().setWrapText(true);
			Font headFont = writer.createFont();
			headFont.setFontHeightInPoints((short) 11);
			writer.getHeadCellStyle().setFont(headFont);
			writer.getHeadCellStyle().setFillPattern(FillPatternType.NO_FILL);
			writer.getOrCreateRow(0).setHeightInPoints(120);
			writer.setRowHeight(-1, 20);
			//set style
			for (int i = 0, k = dataEntities.size(), z = 0; i < k; i++, z++) {
				ExcelDataEntity dataEntity = dataEntities.get(i);
				//The new cell will be added or the current cell will be adjusted, and the default processing will be skipped directly.
				int n = excelParseService.addCell(writer, z, 1, dataEntity.getFieldName());
				if (n > 0) {
					z += n;
					continue;
				}
				CellStyle columnStyle = writer.getOrCreateColumnStyle(z);
				//Set unified font
				columnStyle.setFont(headFont);
				DataFormat dateFormat = writer.getWorkbook().createDataFormat();
				if (Objects.equals(dataEntities.get(i).getType(), FieldEnum.DATE.getType())) {
					columnStyle.setDataFormat(dateFormat.getFormat(DatePattern.NORM_DATE_PATTERN));
				} else if (Objects.equals(dataEntities.get(i).getType(), FieldEnum.DATETIME.getType())) {
					columnStyle.setDataFormat(dateFormat.getFormat(DatePattern.NORM_DATETIME_PATTERN));
				} else {
					columnStyle.setDataFormat(dateFormat.getFormat("@"));
				}
				writer.setColumnWidth(z, 20);
				Cell cell = writer.getOrCreateCell(z, 1);
				//Special handling of required fields
				if (Objects.equals(1, dataEntity.getIsNull())) {
					cell.setCellValue("*" + dataEntity.getName());
					CellStyle cellStyle = writer.getOrCreateCellStyle(z, 1);
					Font cellFont = writer.createFont();
					cellFont.setFontHeightInPoints((short) 11);
					cellFont.setColor(cellFont.COLOR_RED);
					cellStyle.setFont(cellFont);
					cell.setCellStyle(cellStyle);
				} else {
					cell.setCellValue(dataEntity.getName());
				}
				//Select type to add drop-down box
				if (CollUtil.isNotEmpty(dataEntity.getSetting())) {
					String[] array = dataEntity.getSetting().stream().map(data -> {
						if (data instanceof JSONObject && ((JSONObject) data).containsKey("name")) {
							return ((JSONObject) data).getString("name");
						}
						return data.toString();
					}).toArray(String[]::new);
					writer.addSelect(new CellRangeAddressList(2, 10002, z, z), array);
				}
			}
			// custom title alias
			//response is the HttpServletResponse object
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			//test.xls is the file name of the pop-up download dialog, which cannot be Chinese, please encode the Chinese by yourself
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(excelParseService.getExcelName() + "Import Template", "utf-8") + ".xls" + (excelParseService.isXlsx() ? "x" : ""));
			ServletOutputStream out = response.getOutputStream();
			writer.flush(out);
		} catch (Exception e) {
			log.error("Download" + excelParseService.getExcelName() + "Import template error", e);
		}

	}

	/**
	 * @return true
	 */
	public static boolean removeFieldByType(Integer type) {
		return typeList.contains(type);
	}

	public static ExcelDataEntity toEntity(String fieldName, String name) {
		return new ExcelDataEntity(fieldName, name, FieldEnum.TEXT.getType());
	}

	public static ExcelDataEntity toEntity(String fieldName, String name, Integer type) {
		return new ExcelDataEntity(fieldName, name, type);
	}

	@Data
	public static class ExcelDataEntity {

		private String fieldName;

		private String name;

		private Integer type;

		private Integer isNull;

		private List<Object> setting;

		public ExcelDataEntity() {
		}

		public ExcelDataEntity(String fieldName, String name, Integer type) {
			this.fieldName = fieldName;
			this.name = name;
			this.type = type;
		}
	}

	public static abstract class ExcelParseService {


		/**
		 * @param list
		 * @param importExcel
		 * @return
		 */
		public List<ExcelDataEntity> parseData(List<?> list, boolean importExcel) {
			List<ExcelDataEntity> entities = list.stream().map(obj -> {
				if (obj instanceof ExcelDataEntity) {
					return (ExcelDataEntity) obj;
				}
				return BeanUtil.copyProperties(obj, ExcelDataEntity.class);
			}).collect(Collectors.toList());
			if (importExcel) {
				entities.removeIf(head -> ExcelParseUtil.removeFieldByType(head.getType()));
			} else {
				entities.removeIf(head -> FieldEnum.HANDWRITING_SIGN.getType().equals(head.getType()));
			}
			return entities;
		}

		/**
		 * @param record
		 */
		public abstract void castData(Map<String, Object> record, Map<String, Integer> headMap);

		/**
		 * @return
		 */
		public abstract String getExcelName();

		/**
		 * @param writer    writer
		 * @param x         – X，0，
		 * @param y         – Y，0，
		 * @param fieldName
		 * @return
		 */
		public int addCell(ExcelWriter writer, Integer x, Integer y, String fieldName) {
			return 0;
		}

		/**
		 * @return isXlsx
		 */
		public boolean isXlsx() {
			return false;
		}

		public String getMergeContent(String module) {
			if (module != null && module.equals("user")) {
				return "Note:\n" +
						"1. The red font marked in the header is required\n" +
						"2. Mobile phone number: Currently only 11-digit mobile phone numbers in mainland China are supported; and mobile phone numbers are not allowed to be repeated\n" +
						"3. Login password: The password consists of 6-20 letters and numbers\n" +
						"4. Department: The upper and lower departments are separated by \"/\", and start from the top department, such as Seoul Branch/Marketing Department/Marketing One. If the same department appears, the organizational structure will be imported by default Department in the top order\n";
			} else {
				return "Note:\n" +
						"1. The red font marked in the header is required\n" +
						"2. Date time: The recommended format is 2020-02-02 13:13:13\n" +
						"3. Date: The recommended format is 2020-02-02\n" +
						"4. Mobile phone number: supports 6-15 digits (including foreign mobile phone number format)\n" +
						"5. Mailbox: Only the mailbox format is supported\n" +
						"6. Multi-line text: character limit is 800 characters";
			}
		}


	}
}
