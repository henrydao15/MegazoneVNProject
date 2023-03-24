package com.megazone.hrm.constant;

public enum QuitTypeEnum {
	/**
	 * Type of resignation
	 */
	INITIATIVE(1, "active resignation"), PASSIVE(2, "passive resignation");

	private String name;
	private int value;

	QuitTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (QuitTypeEnum value : QuitTypeEnum.values()) {
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
