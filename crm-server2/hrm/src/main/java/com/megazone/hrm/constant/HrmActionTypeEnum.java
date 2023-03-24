package com.megazone.hrm.constant;

public enum HrmActionTypeEnum {
	/**
	 * Operation record type type enumeration
	 */
	EMPLOYEE(1, "Employee"), RECRUIT_POST(2, "Job Position"), RECRUIT_CANDIDATE(3, "Candidate"),
	EMPLOYEE_APPRAISAL(4, "Employee Performance");

	private String name;
	private int value;

	HrmActionTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (HrmActionTypeEnum value : HrmActionTypeEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (HrmActionTypeEnum value : HrmActionTypeEnum.values()) {
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
