package com.megazone.hrm.constant;

public enum IsEnum {
	/**
	 * Is the type enumeration 0 no 1 yes
	 */
	YES(1, "Yes"), NO(0, "No");

	private String name;
	private int value;

	IsEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (IsEnum value : IsEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (IsEnum value : IsEnum.values()) {
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
