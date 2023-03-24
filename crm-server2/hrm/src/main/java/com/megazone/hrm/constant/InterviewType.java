package com.megazone.hrm.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum InterviewType {
	/**
	 * Enumeration of interview methods 1 On-site interview 2 Telephone interview 3 Video interview
	 */
	SITE(1, "On-site interview"), PHONE(2, "Phone interview"), VIDEO(3, "Video interview");

	private String name;
	private int value;

	InterviewType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (InterviewType value : InterviewType.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (InterviewType value : InterviewType.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

	public static List<Map<String, Object>> parseMap() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (InterviewType value : InterviewType.values()) {
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
