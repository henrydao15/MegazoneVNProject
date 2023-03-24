package com.megazone.hrm.constant;

public enum SalaryChangeTypeEnum {
	/**
	 * Salary adjustment status 0 Unscheduled salary 1 Salary fixed 2 Salary adjusted
	 */
	NOT_FIX_SALARY(0, "Undetermined salary"), HAS_FIX_SALARY(1, "salary fixed"), HAS_CHANGE_SALARY(2, "salary adjusted");

	private String name;
	private int value;

	SalaryChangeTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (SalaryChangeTypeEnum value : SalaryChangeTypeEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (SalaryChangeTypeEnum value : SalaryChangeTypeEnum.values()) {
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
