package com.megazone.hrm.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum BirthdayType {
	/**
	 * Birthday type enumeration
	 */
	SOLAR_CALENDAR(1, "Gregorian calendar"), LUNAR_CALENDAR(2, "Lunar calendar");

	private String name;
	private int value;

	BirthdayType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (BirthdayType value : BirthdayType.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (BirthdayType value : BirthdayType.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

	public static List<Map<String, Object>> parseMap() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (BirthdayType value : BirthdayType.values()) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", value.name);
			map.put("value", value.value);
			mapList.add(map);
		}
		return mapList;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
}
