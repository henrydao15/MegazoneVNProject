package com.megazone.hrm.constant;

/**
 * Contract status
 */
public enum EmployeeContractStatus {
	/**
	 * Contract status In execution, expired, not executed
	 */
	NOT_PERFORMED(0, "not executed"), IN_PROGRESS(1, "executed"), BE_EXPIRED(2, "expired");

	private String name;
	private int value;

	EmployeeContractStatus(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (EmployeeContractStatus value : EmployeeContractStatus.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (EmployeeContractStatus value : EmployeeContractStatus.values()) {
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
