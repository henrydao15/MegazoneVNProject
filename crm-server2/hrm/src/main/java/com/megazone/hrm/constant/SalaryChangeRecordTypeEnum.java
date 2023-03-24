package com.megazone.hrm.constant;

public enum SalaryChangeRecordTypeEnum {
	/**
	 * Salary adjustment record type 1 Fixed salary 2 Salary adjustment
	 */
	FIX_SALARY(1, "fixed salary"), CHANGE_SALARY(2, "salary adjustment");

	private String name;
	private int value;

	SalaryChangeRecordTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (SalaryChangeRecordTypeEnum value : SalaryChangeRecordTypeEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (SalaryChangeRecordTypeEnum value : SalaryChangeRecordTypeEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}


	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
}
