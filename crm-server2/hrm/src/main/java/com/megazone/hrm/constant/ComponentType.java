package com.megazone.hrm.constant;

public enum ComponentType {

	/**
	 * Association table type 0 No association required 1 hrm employee 2 hrm department 3 hrm position 4 system user 5 system department 6 recruitment channel
	 */
	NO(0, "No association required"), HRM_EMPLOYEE(1, "hrm employee"), HRM_DEPT(2, "hrm department"), HRM_POST(3, "hrm position"),
	ADMIN_USER(4, "System User"), ADMIN_DEPT(5, "System Department"), RECRUIT_CHANNEL(6, "Recruitment Channel"), HOMETOWN(7, "Hometown");

	private String name;
	private int value;

	ComponentType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
}
