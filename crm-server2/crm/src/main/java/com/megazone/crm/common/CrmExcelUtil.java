package com.megazone.crm.common;


import com.megazone.core.common.JSONObject;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CrmExcelUtil {

	private static final Map<String, List<String>> areaMap;

	static {
		try {
			areaMap = JSONObject.parseObject(Objects.requireNonNull(CrmExcelUtil.class.getClassLoader().getResourceAsStream("area.json")), HashMap.class);
		} catch (IOException e) {
			throw new CrmException(SystemCodeEnum.SYSTEM_ERROR);
		}
	}

	public CrmExcelUtil() {
	}

	public static String getRange(int offset, int rowId, int colCount) {
		char start = (char) ('A' + offset);
		if (colCount <= 25) {
			char end = (char) (start + colCount - 1);
			return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
		} else {
			char endPrefix = 'A';
			char endSuffix = 'A';
			if ((colCount - 25) / 26 == 0 || colCount == 51) {// 26-51，（）
				if ((colCount - 25) % 26 == 0) {//
					endSuffix = (char) ('A' + 25);
				} else {
					endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
				}
			} else {// 51
				if ((colCount - 25) % 26 == 0) {
					endSuffix = (char) ('A' + 25);
					endPrefix = (char) (endPrefix + (colCount - 25) / 26 - 1);
				} else {
					endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
					endPrefix = (char) (endPrefix + (colCount - 25) / 26);
				}
			}
			return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;
		}
	}

	/**
	 * offset, ，
	 */
	public static void setDataValidation(String offset, Sheet sheet, int rowNum, int colNum) {
		int i = rowNum + 1;
		DVConstraint formula = DVConstraint.createFormulaListConstraint("INDIRECT($" + offset + "$" + i + ")");
		CellRangeAddressList rangeAddressList = new CellRangeAddressList(rowNum, rowNum, colNum, colNum);
		HSSFDataValidation cacse = new HSSFDataValidation(rangeAddressList, formula);
		cacse.createErrorBox("error", "Please select the correct region");
		sheet.addValidationData(cacse);
	}

	/**
	 * @param columnNo
	 * @return
	 */
	public static String getCorrespondingLabel(int columnNo) {
		if (columnNo < 1 || columnNo > 16384) {
			throw new IllegalArgumentException();
		}
		String[] sources = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"
				, "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		StringBuilder sb = new StringBuilder(5);

		int remainder = columnNo % 26;

		if (remainder == 0) {
			sb.append("Z");

			remainder = 26;
		} else {
			sb.append(sources[remainder - 1]);
		}

		columnNo = (columnNo - remainder) / 26 - 1;


		while (columnNo > -1) {
			remainder = columnNo % 26;
			sb.append(sources[remainder]);
			columnNo = (columnNo - remainder) / 26 - 1;
		}

		return sb.reverse().toString();
	}

	/**
	 * @return
	 */
	public static Map<String, List<String>> getAreaMap() {
		return areaMap;
	}
}
