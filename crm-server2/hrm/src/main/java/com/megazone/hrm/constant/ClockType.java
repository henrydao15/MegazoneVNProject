package com.megazone.hrm.constant;

import lombok.Getter;

/**
 * @author
 */

@Getter
public enum ClockType {

	/**
	 * Punch card type
	 */
	GO_TO(1, "Go to work"),
	GET_OFF(2, "off work");

	private int value;
	private String name;

	ClockType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String valueOfName(Integer value) {
		for (ClockType clockType : values()) {
			if (clockType.value == value) {
				return clockType.name;
			}
		}
		return null;
	}
}
