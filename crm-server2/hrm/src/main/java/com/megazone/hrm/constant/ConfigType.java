package com.megazone.hrm.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ConfigType {
	/**
	 * Human resource configuration type enumeration
	 */
	ELIMINATION_REASONS(1, "Reason for elimination"), SALARY_INIT_CONFIG1(2, "Salary initialization configuration 1"), SALARY_INIT_CONFIG2(3, "Salary initialization configuration 2"), INSURANCE_INIT_CONFIG1(4, "Social security initialization configuration 1"), INSURANCE_INIT_CONFIG2(5, "Social security initialization configuration 2");

	private String name;
	private int value;

	ConfigType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (ConfigType value : ConfigType.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (ConfigType value : ConfigType.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

	public static List<Map<String, Object>> parseMap() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (ConfigType value : ConfigType.values()) {
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
