package com.megazone.hrm.constant;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum SexEnum {
	/**
	 * Gender enumeration
	 */
	UNFILLED(-1, "unfilled"), MAN(1, "male"), WOMAN(2, "female");

	private int value;
	private String name;

	SexEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (SexEnum value : SexEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}


	public static int valueOfType(String name) {
		for (SexEnum value : SexEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

	public static List<Map<String, Object>> parseMap() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (SexEnum value : SexEnum.values()) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", value.name);
			map.put("value", value.value);
			mapList.add(map);
		}
		return mapList;
	}
}
