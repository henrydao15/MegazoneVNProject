package com.megazone.hrm.utils;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.awt.Color;

/**
 * @author
 */
public final class SalaryExcelUtil {

	private static final Color HEAD_COLOR = new Color(197, 217, 241);

	/**
	 * @param wb
	 * @param row
	 * @param column
	 * @param value
	 */
	public static void createHeadCell(XSSFWorkbook wb, Row row, int column, String value) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFillForegroundColor(new XSSFColor(HEAD_COLOR, new DefaultIndexedColorMap()));
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		XSSFFont font = wb.createFont();
		font.setBold(true);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	public static void createBodyCell(XSSFWorkbook wb, Row row, int column, String value) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value);
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		XSSFFont font = wb.createFont();
		font.setBold(false);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}
}
