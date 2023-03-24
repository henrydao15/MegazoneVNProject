package com.megazone.hrm.constant;

import lombok.Getter;

@Getter
public enum TaxType {
	/**
	 * Gender enumeration
	 */
	SALARY_TAX_TYPE(1, "salary and salary"), REMUNERATION_TAX_TYPE(2, "labor remuneration"), NO_TAX_TYPE(3, "tax excluded");

	private int value;
	private String name;

	TaxType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static TaxType parse(int type) {
		for (TaxType value : TaxType.values()) {
			if (value.value == type) {
				return value;
			}
		}
		return SALARY_TAX_TYPE;
	}

}
