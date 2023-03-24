package com.megazone.hrm.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EmploymentFormsEnum {

	/**
	 * Enumeration of employment forms
	 */
	OFFICIAL(1, "official"), NO_OFFICIAL(2, "informal");

	private int value;
	private String name;

	EmploymentFormsEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (EmploymentFormsEnum value : EmploymentFormsEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}


	public static int valueOfType(String name) {
		for (EmploymentFormsEnum value : EmploymentFormsEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

	public static List<Map<String, Object>> parseMap() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (EmploymentFormsEnum value : EmploymentFormsEnum.values()) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", value.name);
			map.put("value", value.value);
			mapList.add(map);
		}
		return mapList;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
