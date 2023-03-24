package com.megazone.hrm.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EmployeeContractType {
	/**
	 * 1. Fixed-term labor contract 2. Unfixed-term labor contract 3. Labor contract for which certain work tasks have been completed 4. Internship agreement 5. Labor contract 6. Reemployment agreement 7. Labor dispatch contract 8. Secondment contract 9. Others
	 */
	FIXED_TERM_LABOR_CONTRACT(1, "Fixed term labor contract"), NO_FIXED_TERM_LABOR_CONTRACT(2, "No fixed term labor contract"), WORK_TASK_LABOR_CONTRACT(3, "A labor contract with a certain duration of work has been completed"), INTERNSHIP_AGREEMENT(4, "Internship agreement"), LABOR_CONTRACT(5, "labor contract"), REEMPLOYMENT_AGREEMENT(6, "reemployment agreement"), LABOR_DISPATCH_CONTRACT(7, "labor dispatch contract"), SECONDMENT_CONTRACT(8, "Secondment contract"), OTHER(9, "Other");


	private String name;
	private int value;

	EmployeeContractType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (EmployeeContractType value : EmployeeContractType.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (EmployeeContractType value : EmployeeContractType.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

	public static List<Map<String, Object>> parseMap() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (EmployeeContractType value : EmployeeContractType.values()) {
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
