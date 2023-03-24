package com.megazone.hrm.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EmployeeStatusEnum {
	/**
	 * Employee status
	 */
	OFFICIAL(1, 1, "Official"), TRY_OUT(1, 2, "Trial"),
	INTERNSHIP(2, 3, "Internship"), PART_TIME(2, 4, "Part-time"),
	SERVICE(2, 5, "Labor"), CONSULTANT(2, 6, "Consultant"),
	RETURN(2, 7, "Rehiring"), OUTSOURCING(2, 8, "Outsourcing"),
	;

	private int type;
	private int value;
	private String name;

	EmployeeStatusEnum(int type, int value, String name) {
		this.type = type;
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (EmployeeStatusEnum value : EmployeeStatusEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (EmployeeStatusEnum value : EmployeeStatusEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

	public static List<Map<String, Object>> parseMapByType(int type) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (EmployeeStatusEnum value : EmployeeStatusEnum.values()) {
			if (type == value.type) {
				Map<String, Object> map = new HashMap<>();
				map.put("name", value.name);
				map.put("value", value.value);
				mapList.add(map);
			}
		}
		return mapList;
	}

	public static List<Map<String, Object>> parseMap() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (EmployeeStatusEnum value : EmployeeStatusEnum.values()) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", value.name);
			map.put("value", value.value);
			mapList.add(map);
		}
		return mapList;
	}

	public int getType() {
		return type;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
