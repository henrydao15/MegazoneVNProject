package com.megazone.hrm.constant;

public enum HrmActionBehaviorEnum {

	/**
	 * Operational Behavior 1 New 2 Edit 3 Delete 4 Regular 5 Transfer 6 Promotion 7 Demotion 8 Full-time Employee 9 Resignation 10 Insurance Plan
	 */
	ADD(1, "New"), UPDATE(2, "Edit"), DELETE(3, "Delete"),
	BECOME(4, "Turn to positive"),
	CHANGE_POST(5, "transfer"), PROMOTED(6, "promotion"), DEGRADE(7, "demotion"),
	CHANGE_FULL_TIME_EMPLOYEE(8, "Convert to full-time employee"), QUIT(9, "Leave"),
	PARTICIPATION_PLAN(10, "Participation plan");


	private String name;
	private int value;

	HrmActionBehaviorEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (HrmActionBehaviorEnum value : HrmActionBehaviorEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (HrmActionBehaviorEnum value : HrmActionBehaviorEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}

	public static HrmActionBehaviorEnum parse(int type) {
		for (HrmActionBehaviorEnum value : HrmActionBehaviorEnum.values()) {
			if (value.value == type) {
				return value;
			}
		}
		return BECOME;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}


}
