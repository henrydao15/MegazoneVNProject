package com.megazone.hrm.constant;

public enum SalaryChangeRecordStatusEnum {
	/**
	 * Salary adjustment record status 0 Not effective 1 Effective 2 Cancelled
	 */
	NOT_TAKE_EFFECT(0, "not effective"), HAS_TAKE_EFFECT(1, "effective"), CANCEL(2, "cancelled");

	private String name;
	private int value;

	SalaryChangeRecordStatusEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (SalaryChangeRecordStatusEnum value : SalaryChangeRecordStatusEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (SalaryChangeRecordStatusEnum value : SalaryChangeRecordStatusEnum.values()) {
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
