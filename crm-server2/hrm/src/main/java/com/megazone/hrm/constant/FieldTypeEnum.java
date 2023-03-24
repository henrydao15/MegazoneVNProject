package com.megazone.hrm.constant;

public enum FieldTypeEnum {

	/**
	 * Custom field type
	 * Field type 1 Single-line text 2 Multi-line text 3 Single choice 4 Date 5 Number 6 Decimal 7 Mobile phone 8 File 9 Multiple choice 10 Date and time 11 Email 12 Origin area
	 */
	TEXT(1, "Single line text"),
	TEXTAREA(2, "Multi-line text"),
	SELECT(3, "Single selection"),
	DATE(4, "date"),
	NUMBER(5, "Number"),
	DECIMAL(6, "decimal"),
	MOBILE(7, "Mobile"),
	FILE(8, "File"),
	CHECKBOX(9, "multiple choice"),
	DATETIME(13, "datetime"),
	EMAIL(14, "Mailbox"),
	AREA(40, "province city"),
	;

	private String name;
	private int value;


	FieldTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static FieldTypeEnum parse(int formType) {
		for (FieldTypeEnum value : FieldTypeEnum.values()) {
			if (value.value == formType) {
				return value;
			}
		}
		return TEXT;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
}
