package com.megazone.hrm.constant;

public enum SalaryChangeReasonEnum {
	/**
	 * Reasons for salary adjustment 1 Approved for entry 2 Regularization 3 Promotion 4 Transfer 5 Mid-year salary adjustment 6 Annual salary adjustment 7 Special salary adjustment 8 Others
	 */
	ENTRY_FIX_SALARY(0, "Entry Salary"), ENTRY_APPROVAL(1, "Entry Approval"), BECOME(2, "Regularization"), PROMOTION(3, "Promotion"),
	TRANSFER(4, "Transfer"), MID_YEAR_SALARY_CHANGE(5, "Mid-year salary adjustment"), YEAR_SALARY_CHANGE(6, "Annual salary adjustment"),
	SPECIAL_SALARY_CHANGE(7, "Special salary adjustment"), OTHER(8, "Other"),

	;

	private String name;
	private int value;

	SalaryChangeReasonEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (SalaryChangeReasonEnum value : SalaryChangeReasonEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (SalaryChangeReasonEnum value : SalaryChangeReasonEnum.values()) {
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
