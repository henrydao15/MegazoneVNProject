package com.megazone.hrm.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum IdTypeEnum {
	/**
	 * Document type enumeration
	 */
	ID_CARD(1, "ID Card"), PASSPORT(2, "Passport"), OTHER(3, "Other");

	private String name;
	private int value;

	IdTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (IdTypeEnum value : IdTypeEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (IdTypeEnum value : IdTypeEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

	public static List<Map<String, Object>> parseMap() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (IdTypeEnum value : IdTypeEnum.values()) {
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
