package com.megazone.hrm.constant;

public enum FiledIsFixedEnum {
	/**
	 * Is it a fixed field
	 */
	FIXED(1, "Fixed Field"), NO_FIXED(0, "Custom");

	private String name;
	private int value;

	FiledIsFixedEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (FiledIsFixedEnum value : FiledIsFixedEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (FiledIsFixedEnum value : FiledIsFixedEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

	public static FiledIsFixedEnum parse(int type) {
		for (FiledIsFixedEnum value : FiledIsFixedEnum.values()) {
			if (value.value == type) {
				return value;
			}
		}
		return FIXED;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
}
