package com.megazone.oa.common;

import com.megazone.oa.entity.PO.OaExamineField;

import java.util.List;

public class FieldUtil {
	private List<OaExamineField> recordList;


	public FieldUtil() {
	}

	public FieldUtil(List<OaExamineField> recordList) {
		this.recordList = recordList;
	}

	public List<OaExamineField> getRecordList() {
		return recordList;
	}

	public FieldUtil oaFieldAdd(String fieldName, String name, String formType, List<String> settingArr, Integer isNull,
								Integer isUnique, Object value, String defaultValue, Integer operating, Integer fieldType,
								String formPosition) {
		OaExamineField record = new OaExamineField();
		record.setFieldName(fieldName);
		record.setName(name);
		record.setMaxLength(0);
		record.setIsUnique(isUnique);
		record.setIsNull(isNull);
		record.setOperating(operating);
		record.setFieldType(fieldType);
		record.setFormType(formType);
		record.setSetting(settingArr);
		record.setValue(value);
		record.setDefaultValue(defaultValue);
		record.setFormPosition(formPosition);
		record.setStylePercent(100);
		record.setSorting(0);
		recordList.add(record);
		return this;
	}

}
