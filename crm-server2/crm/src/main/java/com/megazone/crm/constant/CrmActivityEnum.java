package com.megazone.crm.constant;


public enum CrmActivityEnum {
	/**
	 * crm activity enumeration
	 */
	LEADS(1, "Lead"),
	CUSTOMER(2, "Customer"),
	CONTACTS(3, "Contact"),
	PRODUCT(4, "Product"),
	BUSINESS(5, "Business Opportunity"),
	CONTRACT(6, "Contract"),
	RECEIVABLES(7, "Cashback"),
	LOG(8, "Log"),
	EXAMINE(9, "Approval"),
	EVENT(10, "Schedule"),
	TASK(11, "task"),
	MAIL(12, "send mail");

	private final int type;
	private final String remarks;

	CrmActivityEnum(int type, String remarks) {
		this.type = type;
		this.remarks = remarks;
	}

	public static CrmActivityEnum parse(int type) {
		for (CrmActivityEnum value : CrmActivityEnum.values()) {
			if (value.type == type) {
				return value;
			}
		}
		return null;
	}


	public int getType() {
		return type;
	}

	public String getRemarks() {
		return remarks;
	}
}
