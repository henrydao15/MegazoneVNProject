package com.megazone.hrm.constant;

public enum CandidateEducationEnum {

	// 1 primary school 2 junior high school 3 high school 4 college 5 undergraduate 6 master 7 doctor
	PRIMARY_SCHOOL(1, "Primary School"), JUNIOR_HIGH_SCHOOL(2, "Junior High School"),
	HIGH_SCHOOL(3, "High School"), JUNIOR_COLLEGE(4, "College"),
	UNDERGRADUATE(5, "Undergraduate"), MASTER(6, "Master"),
	DOCTOR(7, "Doctor");

	private int value;
	private String name;

	CandidateEducationEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (CandidateEducationEnum value : CandidateEducationEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}


	public static int valueOfType(String name) {
		for (CandidateEducationEnum value : CandidateEducationEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

}
