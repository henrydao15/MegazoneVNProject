package com.megazone.admin.common;


public enum AdminModuleEnum {

	TASK_EXAMINE("taskExamine"),
	/**
	 * crm module
	 */
	CRM("crm"),
	/**
	 * project management
	 */
	PROJECT("project"),
	/**
	 * log module
	 */
	LOG("log"),
	/**
	 * Contacts module
	 */
	BOOK("book"),
	/**
	 * Office module
	 */
	OA("oa"),
	/**
	 * Business Intelligence Module
	 */
	BI("bi"),
	/**
	 * Mailbox module
	 */
	EMAIL("email"),
	/**
	 * Calendar module
	 */
	CALENDAR("calendar"),
	/**
	 * Knowledge base
	 */
	KNOWLEDGE("knowledge"),
	/**
	 * Call Center
	 */
	CALL("call"),

	HRM("hrm"),

	JXC("jxc");

	private String value;

	private AdminModuleEnum(String value) {
		this.value = value;
	}

	public static String[] getValues() {
		String[] values = new String[values().length];
		for (int i = 0; i < values().length; i++) {
			values[i] = values()[i].getValue();
		}
		return values;
	}

	public static AdminModuleEnum parse(String module) {
		for (AdminModuleEnum adminModuleEnum : AdminModuleEnum.values()) {
			if (adminModuleEnum.getValue().equals(module)) {
				return adminModuleEnum;
			}
		}
		return null;
	}

	public String getValue() {
		return this.value;
	}

}
