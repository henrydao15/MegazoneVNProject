package com.megazone.hrm.constant;

/**
 * Enumeration of educational methods
 */
public enum TeachingMethodsEnum {
	/**
	 * System default: 1 full-time, 2 adult education, 3 distance education, 4 self-study exam, 5 others
	 */
	FULL_TIME(1, "full-time"), ADULT_EDUCATION(2, "adult education"), DISTANCE_EDUCATION(3, "distance education"),
	SELF_EXAMINATION(4, "Self-study exam"), OTHER(5, "Other");

	private String name;
	private int value;

	TeachingMethodsEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (TeachingMethodsEnum value : TeachingMethodsEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (TeachingMethodsEnum value : TeachingMethodsEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}
}
