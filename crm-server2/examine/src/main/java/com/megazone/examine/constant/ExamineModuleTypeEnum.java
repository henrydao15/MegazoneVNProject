package com.megazone.examine.constant;

/**
 *
 */
public enum ExamineModuleTypeEnum {
	Crm,
	Jxc,
	Hrm,
	Oa,
	;

	public String getServerName() {
		return "examine" + name() + "Service";
	}

}
