package com.megazone.hrm.constant.achievement;

public enum EmployeeAppraisalStatus {
	/**
	 * Assessment status 1 To be filled 2 To be confirmed 3 To be assessed 4 To be confirmed 5 To terminate performance
	 */
	TO_BE_FILLED(1, "to be filled in"), PENDING_CONFIRMATION(2, "to be confirmed by the target"),
	TO_BE_ASSESSED(3, "to be evaluated by the result"), TO_BE_CONFIRMED(4, "to be confirmed by the result"),
	STOP(5, "Termination of performance"), COMPLETE(6, "Completion of assessment");

	private String name;
	private int value;

	EmployeeAppraisalStatus(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (EmployeeAppraisalStatus value : EmployeeAppraisalStatus.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (EmployeeAppraisalStatus value : EmployeeAppraisalStatus.values()) {
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
