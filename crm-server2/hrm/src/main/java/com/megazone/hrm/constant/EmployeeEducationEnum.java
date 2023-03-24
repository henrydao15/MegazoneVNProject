package com.megazone.hrm.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EmployeeEducationEnum {

	// 1 primary school, 2 junior high school, 3 technical secondary school, 4 secondary vocational school, 5 technical secondary school, 6 high school, 7 junior college, 8 undergraduate, 9 master, 10 doctor, 11 postdoctoral fellow, 12 other
	PRIMARY_SCHOOL(1, "Primary School"), JUNIOR_HIGH_SCHOOL(2, "Junior High School"),
	TECHNICAL_SECONDARY_SCHOOL(3, "Secondary school"), VOCATIONAL(4, "Secondary vocational"), ZHONGJI(5, "Secondary technical"),
	HIGH_SCHOOL(6, "High School"), JUNIOR_COLLEGE(7, "College"),
	UNDERGRADUATE(8, "Undergraduate"), MASTER(9, "Master"),
	DOCTOR(10, "Doctor"), POSTDOCTOR(11, "Postdoc"), OTHER(12, "Other");

	private int value;
	private String name;

	EmployeeEducationEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (EmployeeEducationEnum value : EmployeeEducationEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}


	public static int valueOfType(String name) {
		for (EmployeeEducationEnum value : EmployeeEducationEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

	public static List<Map<String, Object>> parseMap() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (EmployeeEducationEnum value : EmployeeEducationEnum.values()) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", value.name);
			map.put("value", value.value);
			mapList.add(map);
		}
		return mapList;
	}
}
