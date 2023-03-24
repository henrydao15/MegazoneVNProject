package com.megazone.hrm.constant;

public enum PostEducationEnum {

	// 1 no limit 2 high school and above 3 college and above 4 undergraduate and above 5 master and above 6 doctor
	UNLIMITED(1, "Unlimited"), HIGH_SCHOOL_UP(2, "High school and above"), JUNIOR_COLLEGE_UP(3, "College and above"),
	UNDERGRADUATE_UP(4, "Undergraduate and above"), MASTER_UP(5, "Master's degree and above"),
	DOCTOR(6, "Doctor");

	private int value;
	private String name;

	PostEducationEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (PostEducationEnum value : PostEducationEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}


	public static int valueOfType(String name) {
		for (PostEducationEnum value : PostEducationEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

}
