package com.megazone.hrm.constant.achievement;


public enum AppraisalStatus {
	/**
	 * Performance status 0 Not open for assessment 1 Performance filling in 2 Performance evaluation in progress 3 Result confirmation in progress 4 Archive
	 */
	UNOPENED(0, "Appraisal not enabled"), FILLING_IN(1, "Performance filling in"),
	UNDER_EVALUATION(2, "Performance evaluation"), CONFIRMING(3, "Result confirmation"),
	ARCHIVE(4, "Archive");

	private String name;
	private int value;

	AppraisalStatus(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (AppraisalStatus value : AppraisalStatus.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static AppraisalStatus parse(int type) {
		for (AppraisalStatus value : AppraisalStatus.values()) {
			if (value.value == type) {
				return value;
			}
		}
		return FILLING_IN;
	}

	public static int valueOfType(String name) {
		for (AppraisalStatus value : AppraisalStatus.values()) {
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
