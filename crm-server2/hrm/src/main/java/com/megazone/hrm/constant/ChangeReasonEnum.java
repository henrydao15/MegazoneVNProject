package com.megazone.hrm.constant;

public enum ChangeReasonEnum {
	/**
	 * Reasons for change 1 Organizational structure adjustment 2 Personal application 3 Work arrangement 4 Violation of laws and disciplines 5 Performance not up to standard 6 Personal reasons 7 Not suitable for the current position
	 */
	ORGANIZATIONAL_STRUCTURE_ADJUSTMENT(1, "Organization structure adjustment"), INDIVIDUAL_APPLICATION(2, "Personal application"), WORKING_ARRANGEMENT(3, "Work arrangement"),
	VIOLATION_OF_RULES(4, "violation of discipline and discipline"), NOT_TO_STANDARD(5, "Performance not up to standard"), HEALTH_ISSUES(6, "Personal health reasons"), NOT_SUITABLE(7, "Not suitable for current position");

	private String name;
	private int value;

	ChangeReasonEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (ChangeReasonEnum value : ChangeReasonEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
}
