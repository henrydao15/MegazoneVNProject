package com.megazone.hrm.constant;

import lombok.Getter;

@Getter
public enum EmployeeFileType {
	/**
	 * 11. Original ID card 12, education certificate 13, personal ID photo 14, copy of ID card 15, salary bank card 16, social security card 17, provident fund card 18, award certificate 19, other 21, labor contract 22, resume 23 , Entry Registration Form 24, Entry Physical Examination Form 25, Resignation Certificate from the Previous Company 26, Application Form for Regularization 27, Others 31, Resignation Approval 32, Resignation Certificate 33, Others
	 */
	//Basic Information
	ORIGINAL_ID(11, "Original ID Card"), EDUCATION_CERTIFICATE(12, "Academic Certificate"), PERSONAL_ID_PHOTO(13, "Personal ID Photo"), COPY_OF_ID_CARD(14, "Copy of ID Card"),
	payroll_bank_card(15, "salary bank card"), SOCIAL_SECURITY_CARD(16, "social security card"), PROVIDENT_FUND_CARD(17, "provider fund card"), AWARD_CERTIFICATE(18, "award certificate"),
	INFORMATION_OTHER(19, "Basic information other"),
	//file data
	LABOR_CONTRACT(21, "Labor Contract"), ENTRY_RESUME(22, "Job Resume"), ENTRY_REGISTRATION_FORM(23, "Job Registration Form"), ENTRY_MEDICAL_CHECKLIST(24, "Job Medical Form"), BEFORE_PROOF_OF_SEPARATION(25, "Previous Company Resignation certificate"), CORRECTION_APPLICATION_FORM(26, "Regular application form"), RECORD_OTHER(27, "Other files"),
	// resignation information
	RESIGNATION_APPROVAL(31, "Resignation approval"), PROOF_OF_SEPARATION(32, "Resignation certificate"), RESIGNATION_OTHER(33, "Resignation information other");

	private int value;
	private String name;


	EmployeeFileType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (EmployeeFileType value : EmployeeFileType.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}
}
