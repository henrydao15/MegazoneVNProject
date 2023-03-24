package com.megazone.hrm.constant.achievement;


public enum AppraisalEmployeeType {
	/**
	 * 1 Employee himself 2 Direct superior 3 Head of department 4 Head of superior department 5 Designated target confirmer
	 */
	MYSELF(1, "the employee himself"), PARENT(2, "direct superior"),
	MYSELF_DEPT_MAIN(3, "the person in charge of the department"), PARENT_DEPT_MAIN(4, "the person in charge of the superior department"),
	DESIGNATION(5, "Specify target confirmation person");

	private String name;
	private int value;

	AppraisalEmployeeType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (AppraisalEmployeeType value : AppraisalEmployeeType.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static AppraisalEmployeeType parse(int type) {
		for (AppraisalEmployeeType value : AppraisalEmployeeType.values()) {
			if (value.value == type) {
				return value;
			}
		}
		return MYSELF;
	}

	public static int valueOfType(String name) {
		for (AppraisalEmployeeType value : AppraisalEmployeeType.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}


	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}


}
