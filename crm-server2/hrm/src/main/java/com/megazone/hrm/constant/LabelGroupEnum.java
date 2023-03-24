package com.megazone.hrm.constant;

public enum LabelGroupEnum {

	/**
	 * * 1 Employee Personal Information 2 Communication Information 3 Educational Experience 4 Work Experience 5 Certificate/Certificate 6 Training Experience 7 Contact Person
	 * * 11 Job Information 12 Resignation Information
	 * *21 CONTRACT INFORMATION
	 * * 31 Salary Card Information 32 Social Security Information
	 */
	PERSONAL(1, "personal information", "employee"),
	COMMUNICATION(2, "communication information", "communication information"),
	EDUCATIONAL_EXPERIENCE(3, "Educational Experience", "Educational Experience"),
	WORK_EXPERIENCE(4, "Work Experience", "Work Experience"),
	CERTIFICATE(5, "Certificate/Certificate", "Certificate/Certificate"),
	TRAINING_EXPERIENCE(6, "training experience", "training experience"),
	CONTACT_PERSON(7, "Contact", "Contact"),
	POST(11, "Job Information", "Job Information"), QUIT(12, "Resignation Information", "Resignation Information"),
	CONTRACT(21, "Contract Information", "Contract Information"),
	SALARY_CARD(31, "salary card information", "salary card information"),
	SOCIAL_SECURITY(32, "Social Security Information", "Social Security Information"),
	RECRUIT_POST(41, "Recruitment position", "Position"),
	RECRUIT_CANDIDATE(42, "candidate", "candidate");


	//name
	private String name;
	//label
	private int value;
	//Remark
	private String desc;

	LabelGroupEnum(int value, String name, String desc) {
		this.value = value;
		this.name = name;
		this.desc = desc;
	}

	public static LabelGroupEnum parse(int label) {
		for (LabelGroupEnum labelGroupEnum : values()) {
			if (label == labelGroupEnum.value) {
				return labelGroupEnum;
			}
		}
		return PERSONAL;
	}


	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
